package com.example.raymond.myandroidproject.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raymond.myandroidproject.R;
import com.example.raymond.myandroidproject.base.MyApplication;
import com.example.raymond.myandroidproject.base.Session;
import com.example.raymond.myandroidproject.db.SQLiteDBHelper;
import com.example.raymond.myandroidproject.db.SharePrefenceHelper;
import com.example.raymond.myandroidproject.util.StringUtils;
import com.example.raymond.myandroidproject.util.ToastUtils;
import com.example.raymond.myandroidproject.util.ValidatorUtils;
import com.example.raymond.myandroidproject.widget.PhotoDialog;
import com.liji.circleimageview.CircleImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 参考博客：http://blog.csdn.net/fuhao476200/article/details/71487432
 * 郭霖：第二行代码 第二版
 */
public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private PhotoDialog dialog;
    private Uri imageUri;

    private SQLiteDBHelper dbHelper;
    private Session session;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int CROP_PHOTO = 3;

    private CircleImageView circleImageView;
    private EditText edt_account;
    private EditText edt_password;
    private EditText edt_confirmPassword;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();


        circleImageView = findViewById(R.id.iv_headIcon);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new PhotoDialog(mContext, R.style.PhotoDialog);
                dialog.show();
                Button btn_photoAlbum = dialog.findViewById(R.id.btn_photoAlbum);
                btn_photoAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, 1);
                        } else {
                            openAlbum();
                            dialog.dismiss();
                        }
                    }
                });
                Button btn_photoCamera = dialog.findViewById(R.id.btn_photoCamera);
                btn_photoCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                创建File 对象，用于存储拍照后的图片
                        File outputImage = new File(getExternalCacheDir(), "out_image.jpg");
                        dialog.dismiss();
                        try {
                            if (outputImage.exists()) {
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (Build.VERSION.SDK_INT >= 24) {

                            imageUri = FileProvider.getUriForFile(MainActivity.this
                                    , "com.example.raymond.myandroidproject.fileprovider", outputImage);
                        } else {
                            imageUri = Uri.fromFile(outputImage);
                        }
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, TAKE_PHOTO);
                    }
                });
            }
        });
        commitUserInfo();
    }

    /**
     * 校验用户信息,并将用户信息入库
     */
    private void commitUserInfo() {
        String account = edt_account.getText().toString();
        String password = edt_password.getText().toString();
        String confrimpassword = edt_confirmPassword.getText().toString();

        //            创建数据库
        dbHelper = new SQLiteDBHelper(this, "MyDB.db", null, 1);


        if (!StringUtils.isEmpty(account)) {
            if (ValidatorUtils.isEmail(account) || ValidatorUtils.isMobile(account)) {
                if (ValidatorUtils.isPassword(password) && ValidatorUtils.isPassword(confrimpassword)) {
                    if (password.equals(confrimpassword)) {
                        SQLiteDatabase database =  dbHelper.getWritableDatabase();

                        ContentValues values = new ContentValues();
//                        插入用户信息
                        values.put("account",account);
                        values.put("password",password);
                        database.insert("User",null,values);
//                        创建对话,存放在线用户,保存登录用户
                        session = new Session(account,Integer.valueOf(password));
                        MyApplication.appSingleInstance().setSession(session);
                        // TODO: 2018/2/24  测试session 里的属性值是否为空

                    }
                }

            }
        } else if (StringUtils.isEmpty(account)) {
            ToastUtils.showToast(this, "账号不能为空");

        } else if (!ValidatorUtils.isEmail(account) && !ValidatorUtils.isMobile(account)) {
            ToastUtils.showToast(this, "账号必须是手机或邮箱");

        } else if (!password.equals(confrimpassword)) {
            ToastUtils.showToast(this, "前后密码不一致");

        }

    }

    /**
     * 初始化控件
     */
    public void initView() {
        edt_account = findViewById(R.id.edt_account);
        edt_password = findViewById(R.id.edt_password);
        edt_confirmPassword = findViewById(R.id.edt_confirmPassword);
        button = findViewById(R.id.hello);
    }


    /**
     * open album
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    public void onRequestPermissionResult(int requestCode, String[] permission, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(mContext, "ni shengming le quanxian", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data        返回结果处理
     */
    @SuppressLint("WrongViewCast")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
//                    cropPhoto();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        circleImageView = findViewById(R.id.iv_headIcon);
                        circleImageView.setImageBitmap(bitmap);
                        if (bitmap == null) {
                            Toast.makeText(mContext, "Bitmap is null", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (resultCode == RESULT_OK) {
//                        判断手机系统版本
                        if (Build.VERSION.SDK_INT >= 19) {
                            handleImageOnKitKat(data);
                        } else {
                            handleImageBeforeKitKat(data);
                        }
                    }
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    circleImageView = findViewById(R.id.iv_headIcon);
                    circleImageView.setImageBitmap(bitmap);
                }
                break;

            default:
                break;
        }
    }

    /**
     * @param data Android 4.4 之后
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
//            如果是document 类型的Uri,则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_dwonloads")
                        , Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//                如果是content 类型的Uri,则使用普通方式处理
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            displayImage(imagePath);
        }

    }

    /**
     * @param imagePath 显示图片
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            circleImageView = findViewById(R.id.iv_headIcon);
            circleImageView.setImageBitmap(bitmap);
            if (bitmap == null) {
                Toast.makeText(mContext, "Bitmap is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "can' find the picture", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取图片路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
//        通过Uri 和 selectio 来获取这是的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * @param data Android 4.4 调用此方法
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * @return 创建裁剪的保存的位置
     * @throws IOException
     */
    private File createCropImageFile() {
        File outputCropImage = new File(getExternalCacheDir(), "out_crop_image.jpg");
        try {
            if (outputCropImage.exists()) {
                outputCropImage.delete();
            }
            outputCropImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(MainActivity.this
                    , "com.example.raymond.myandroidproject.fileprovider", outputCropImage);
        } else {
            imageUri = Uri.fromFile(outputCropImage);
        }
        return outputCropImage;
    }

    /**
     * 裁剪图片
     *
     * @param
     */
    private void cropPhoto() {
        Intent intent = new Intent("com.android.camera.CROP");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, createCropImageFile());
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, CROP_PHOTO);
    }

}

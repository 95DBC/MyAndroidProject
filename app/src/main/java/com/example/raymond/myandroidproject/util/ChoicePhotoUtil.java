package com.example.raymond.myandroidproject.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static android.support.v4.content.ContextCompat.getExternalCacheDirs;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Raymond 陈徐锋 on 2018/2/7.
 * Email: raymond@hinteen.com
 * 获取照片工具类
 */

public class ChoicePhotoUtil {
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;

    /**
     * 从相册中获取
     */
    private void getByGallery() {

    }

    /**
     * 启动照相机
     */
    private void getByCamera(Context context) {
//        创建File 对象，用于存储拍照后的图片
        File outputImage = new File(String.valueOf(getExternalCacheDirs(context)), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            // TODO: 2018/2/8 这里的第二参数需要根据对应的程序修改包名
            imageUri = FileProvider.getUriForFile(context, "com.example.raymond.myandroidproject", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent,TAKE_PHOTO);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if (requestCode == RESULT_OK){
//                    Bitmap bitmap = BitmapFactory.decodeStream(getCo)
                }
        }
    }

    /**
     * 裁剪图片
     */
    private void clipPhoto() {

    }

}

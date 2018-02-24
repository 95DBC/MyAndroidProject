package com.example.raymond.myandroidproject.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Raymond 陈徐锋 on 2018/2/24.
 * Email: raymond@hinteen.com
 * Description: Toast 工具类
 */

public class ToastUtils {
    private static Toast toast = null; //Toast的对象！

    public static void showToast(Context mContext, String id) {
        if (toast == null) {
            toast = Toast.makeText(mContext, id, Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }
}
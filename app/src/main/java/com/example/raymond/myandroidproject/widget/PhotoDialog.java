package com.example.raymond.myandroidproject.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.raymond.myandroidproject.R;

/**
 * Created by Raymond 陈徐锋 on 2018/2/7.
 * Email: raymond@hinteen.com
 * <p>
 * 自定义Dialog,用于底部实现头像选择
 * 更多自定义可查看如下博客 https://juejin.im/entry/588458bcb123db7389d2074f
 */

public class PhotoDialog extends Dialog {
    private Context context;

    public PhotoDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("ResourceType")
    public PhotoDialog(@NonNull Context context, int themeResId) {
       super(context,themeResId);
       this.context = context;
    }

    protected PhotoDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context,R.layout.bottomdialog,null);
        setContentView(view);
//        点击外部取消对话框
        setCanceledOnTouchOutside(true);

//        设置对话框显示位置
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);

//        设置一些window 的参数，比如y值，实现距离底部有高
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = 10;

        Button cancel = view.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

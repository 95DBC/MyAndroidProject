package com.example.raymond.myandroidproject.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.raymond.myandroidproject.R;
import com.example.raymond.myandroidproject.widget.PhotoDialog;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        Button button = findViewById(R.id.hello);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoDialog dialog = new PhotoDialog(mContext,R.style.PhotoDialog);
                dialog.show();
            }
        });
    }
}

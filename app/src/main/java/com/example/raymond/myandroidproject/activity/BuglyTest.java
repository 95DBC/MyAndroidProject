package com.example.raymond.myandroidproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.raymond.myandroidproject.R;
import com.tencent.bugly.crashreport.CrashReport;

public class BuglyTest extends AppCompatActivity {
    private Button button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugly_test);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CrashReport.testJavaCrash();
                Toast.makeText(BuglyTest.this, "Hello Bugly", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

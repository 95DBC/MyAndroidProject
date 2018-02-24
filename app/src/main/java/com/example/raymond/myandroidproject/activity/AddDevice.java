package com.example.raymond.myandroidproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.raymond.myandroidproject.R;
import com.example.raymond.myandroidproject.db.SQLiteDBHelper;
import com.example.raymond.myandroidproject.db.SharePrefenceHelper;

public class AddDevice extends AppCompatActivity {
    private SQLiteDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
    }

    /**
     * 判断是否第一次启动App
     */
    public void chckLoggingStatus() {
        boolean isFirstRun = (boolean) SharePrefenceHelper.get(this, "isFirst", false);
        if (isFirstRun == true) {
            isFirstRun = false;
            SharePrefenceHelper.put(this, "isFirst", isFirstRun);



            Intent intent = new Intent(this, Aactivity.class);
            startActivity(intent);
        } else {

        }

    }
}

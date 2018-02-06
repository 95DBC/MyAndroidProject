package com.example.raymond.myandroidproject.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Raymond 陈徐锋 on 2018/2/3.
 * Email: raymond@hinteen.com
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String CREATE_USER = "create table User if not exists ";
    public SQLiteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "my_db", factory, 1);
    }

    public SQLiteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, "my_db", factory, 1, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

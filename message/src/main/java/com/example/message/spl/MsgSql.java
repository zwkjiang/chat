package com.example.message.spl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MsgSql extends SQLiteOpenHelper {
    public MsgSql(Context context) {
        super(context,"meg.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table msg(fromname varchar,toname varchar,time varchar,message varchar,type int,typeuser int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

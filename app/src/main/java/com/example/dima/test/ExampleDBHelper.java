package com.example.dima.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Dima on 27.12.2015.
 */
public class ExampleDBHelper extends SQLiteOpenHelper implements BaseColumns{

    public static final String dbName = "sampleDB";
    public static final int dbVersion = 1;
    public static final String dbTableName = "userData";

    //database columns
    public static final String COLUMN_NAME_TEXT_ENTERED = "userText";

    //database create script
    private static final String dbCreate = "create table " +
            dbTableName + " ("
                + BaseColumns._ID + " integer primary key autoincrement,"
                + COLUMN_NAME_TEXT_ENTERED + " text not null"
            +");";

    public ExampleDBHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dbCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

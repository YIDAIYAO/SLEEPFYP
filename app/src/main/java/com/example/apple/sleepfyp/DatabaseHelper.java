package com.example.apple.sleepfyp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="User.db";
    public static final String Table_Name="User_table";
    public static final String COL_1="ID";
    public static final String COL_2="UserName";
    public static final String COL_3="PassWord";
    public static final String COL_4="Phone";
    public static final String COL_5="HeartRate";
    public static final String COL_6="RespiratoryRate";

    public DatabaseHelper(dengluActivity dengluActivity, String databaseName, Context context, int databaseVersion) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db=this.getWritableDatabase();
        SQLiteDatabase db=this.getReadableDatabase();
    }

   /* public DatabaseHelper(ZHUCEActivity zhuceActivity, String databaseName, Object context, int databaseVersion) {
        super(zhuceActivity, databaseName, context, databaseVersion);
    }*/
   public DatabaseHelper(Context context, String name, CursorFactory factory,
                         int version) {
       super(context, DATABASE_NAME, null, 1);
       // TODO Auto-generated constructor stub
   }


   /* @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ Table_Name +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,UserName TEXT,PassWord TEXT,Phone INTEGER,HeartRate INTEGER,Respiratory INTEGER)");
       // db.execSQL("create table username( name varchar(30) primary key,PassWord varchar(30))");
    }*/
   @Override
   public void onCreate(SQLiteDatabase db) {
       // TODO Auto-generated method stub
       db.execSQL("create table username( name varchar(30) primary key,password varchar(30))");
   }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Table_Name);
        onCreate(db);

    }
}

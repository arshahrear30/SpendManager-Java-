package com.arshahrear.spendmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "digital_moneybag", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table expense(id INTEGER primary key autoincrement,amount DOUBLE,reason TEXT,time DOUBLE)");
        db.execSQL("create table income(id INTEGER primary key autoincrement,amount DOUBLE,reason TEXT,time DOUBLE)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade() মেথডটা তখনই কল হয়, যখন আপনার অ্যাপের ডাটাবেস version number পরিবর্তন করা হয়।
        //পুরনো টেবিল ফেলে দিয়ে নতুন টেবিল তৈরি করার জন্য DROP TABLE IF EXISTS ব্যবহার করা হয়।
        db.execSQL("DROP TABLE IF EXISTS expense");

    }
// এতক্ষণ তো ডাটাবেসের টেবিল তৈরি করেছি এখন ইনপুট সিস্টেমটা করব addExpense method
    public void addExpense(double amount, String reason){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues conval=new ContentValues();
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("time",System.currentTimeMillis()); //Chrome search: android get time in milliseconds

        db.insert("expense",null,conval);
        db.close();

    }


}

package com.arshahrear.spendmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//1 java class টা open হলে public class DatabaseHelper এর পাশে extends SQLiteOpenHelper লিখো enter করো
//2 red bulb>> implement method >>ok || Again red bulb>> Create constructor >>ok
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) { // 3 সব কেটে দিয়ে শুধু Context context লিখবো

        super(context, "digital_moneybag", null, 1); //4 Database file name digital_moneybag set করলাম 1st version
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //5 table create

        db.execSQL("create table expense(id INTEGER primary key autoincrement,amount DOUBLE,reason TEXT,time DOUBLE)");
        db.execSQL("create table income(id INTEGER primary key autoincrement,amount DOUBLE,reason TEXT,time DOUBLE)");


    }

    @Override //6
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade() মেথডটা তখনই কল হয়, যখন আপনার অ্যাপের ডাটাবেস version number পরিবর্তন করা হয়।
        //পুরনো টেবিল ফেলে দিয়ে নতুন টেবিল তৈরি করার জন্য DROP TABLE IF EXISTS ব্যবহার করা হয়।
        db.execSQL("DROP TABLE IF EXISTS expense");
        db.execSQL("DROP TABLE IF EXISTS income");

    }


    //7--------------------------------------------এতক্ষণ ডাটাবেসের টেবিল তৈরি করেছি এখন database ইনপুট করব addExpense method
    public void addExpense(double amount, String reason){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues conval=new ContentValues();
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("time",System.currentTimeMillis()); //Chrome search: android get time in milliseconds

        db.insert("expense",null,conval);

    }
    //--------------------------------------------

    //8 same--------------------------------------------database ইনপুট  addIncome method
    public void addIncome(double amount, String reason){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues conval=new ContentValues();
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("time",System.currentTimeMillis()); //Chrome search: android get time in milliseconds

        db.insert("income",null,conval);

    }
    //--------------------------------------------


    //9 ============================================খরচ টাকা table যুক্ত করছি।টোটাল টাকা দেখানোর জন্য select from দিয়ে পুরা expense টেবিল ধরছি নিচে loop দিয়ে যোগ করছি
    public double calculateTotalExpense() {
        double totalExpense=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(  "select * from expense",  null);
        if (cursor!=null && cursor.getCount()>0){
            while ( cursor.moveToNext() ) {
                double amount = cursor.getDouble(  1);
                totalExpense = totalExpense+amount;
            }
        }
        return totalExpense;
    }
//============================================


    //10 same============================================ইনকাম টাকা যে যুক্ত করছি সেটা টোটাল টাকা দেখানোর জন্য
    public double calculateTotalIncome() {
        double totalIncome=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(  "select * from income",  null);
        if (cursor!=null && cursor.getCount()>0){
            while ( cursor.moveToNext() ) {
                double amount = cursor.getDouble(  1);
                totalIncome = totalIncome+amount;
            }
        }
        return totalIncome;
    }
//============================================

    //\\\\\\\\\\\\\\\\\\\\Data show করার জন্য showAllData method create করছি । Database table থাকা data cursor হিসেবে return করবে

    public Cursor showAllExpense(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from expense",null);
        return cursor;



    }


    //\\\\\\\\\\\\\\\\\\\\



    //\\\\\\\\\\\\\\\\\\\\Data show করার জন্য showAllData method create করছি । Database table থাকা data cursor হিসেবে return করবে

    public Cursor showAllIncome(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from income",null);
        return cursor;



    }


    //\\\\\\\\\\\\\\\\\\\\

    //ddddddddddddddddddddddddddddddddddddddddddddddddddddddddd

    public void deleteExpense(String id){


        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM expense WHERE id = " + id);

    }


    //ddddddddddddddddddddddddddddddddddddddddddddddddddddddddd

    //ddddddddddddddddddddddddddddddddddddddddddddddddddddddddd

    public void deleteIncome(String id){


        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM income WHERE id = " + id);

    }


    //ddddddddddddddddddddddddddddddddddddddddddddddddddddddddd


}

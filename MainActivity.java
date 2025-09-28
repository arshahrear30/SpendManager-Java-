package com.arshahrear.spendmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //0 xml create
    //1 declare variable
    //এক Activity থেকে অন্য Activity-তে যান, তখন প্রথম Activity (যেটা ব্যাকগ্রাউন্ডে যায়) onPause() এবং পরে onStop() কল পায়।
    // যখন  Back Press করে বা অন্যভাবে সেই Activity-তে ফিরে আসেন, তখন যে মেথডটি কল হয় onResume()
    TextView tvFinalBalance, tvTotalExpense, tvTotalIncome, tvAddExpense, tvShowAllDataExpense, tvAddIncome, tvShowAllDataIncome;
    DatabaseHelper dbHelper; //সবগুলো class এ এটার কাজ প্রয়োজন পড়ে তাই ধরতে হয়
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //2 initialize variable
        tvFinalBalance = findViewById(R.id.tvFinalBalance);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvAddExpense = findViewById(R.id.tvAddExpense);
        tvShowAllDataExpense = findViewById(R.id.tvShowAllDataExpense);
        tvAddIncome = findViewById(R.id.tvAddIncome);
        tvShowAllDataIncome = findViewById(R.id.tvShowAllDataIncome);
        dbHelper = new DatabaseHelper(this);//সবগুলো class এ এটার কাজ প্রয়োজন পড়ে তাই ধরতে হয়

        //3 DatabaseHelper.java class create  package name উপর mouse right click>> New>>Javaclass>>DatabaseHelper (next guide on his class)
        //4 New >>Activity>>'Empty views Activity' create >>  name: AddData

        //5....................................... tvAddExpense  call
        tvAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddData.EXPENSE=true;//AddData.java class এর একটা স্ট্যাটিক ভ্যারিয়েবল ডিক্লেয়ার করা হয়েছে
                startActivity(new Intent(MainActivity.this, AddData.class));
            }
        });
        //......................................


        //6  ??????????????????????????????????????????????? tvAddIncome call
        tvAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData.EXPENSE=false;
                startActivity(new Intent(MainActivity.this, AddData.class));
            }
        });
        //???????????????????????????????????????????????



        //7 =========
        upDateUI(); //onCreate এর মধ্যে upDateUI() কল করে রাখছি যেটা দিয়ে onPostResume কল করছি
        //=========

    }

//8 ---------------------------পকেটে এখন কত টাকা আছে সেটা দেখানোর জন্য upDateUI() এ tvFinalBalance method কল করে রাখছি
    private void upDateUI() {
        tvTotalExpense.setText("BDT "+dbHelper.calculateTotalExpense()); //upDateUI onPostResume method না বানালে data input দিয়ে
        tvTotalIncome.setText("BDT "+dbHelper.calculateTotalIncome());//back activity তে আসলে income /expense/finalbalance update হয়ে show করতো না
        tvFinalBalance.setText("BDT "+(dbHelper.calculateTotalIncome()-dbHelper.calculateTotalExpense()));
                             }
//---------------------------



    //7.1 ============  mouse right click>>Generate>>override method>>search: onPostResume
    @Override
    protected void onPostResume() {
        super.onPostResume();
        upDateUI();               }
    //=============

}

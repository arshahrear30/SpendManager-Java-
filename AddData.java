package com.arshahrear.spendmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddData extends AppCompatActivity {
//1
    TextView tvTitle;
    EditText edAmount, edReason;
    Button button;
    DatabaseHelper dbHelper;//2

    public static boolean EXPENSE=true; //EXPENSE = true হলে এটা খরচ আর false হলে এটা ইনকাম এভাবেই শর্ত ধরে খেলবো

    //কোনো শর্ত বা অবস্থা প্রকাশ করতে boolean ব্যবহার করা হয়..EXPENSE নামের একটি স্ট্যাটিক ভ্যারিয়েবল ডিক্লেয়ার করা হয়েছে
    //এর টাইপ boolean, এবং সেট করা হয়েছে true ..boolean = হ্যাঁ/না টাইপ ভ্যালু

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data);

        //3
        tvTitle = findViewById(R.id.tvTitle);
        edAmount = findViewById(R.id.edAmount);
        edReason = findViewById(R.id.edReason);
        button = findViewById(R.id.button);
        dbHelper = new DatabaseHelper(this);//2.1





        button.setOnClickListener(new View.OnClickListener() { //4
            @Override
            public void onClick(View v) {

                String sAmount = edAmount.getText().toString();
                String Reason = edReason.getText().toString();
               double amount = Double.parseDouble(sAmount);

                if(EXPENSE==true) { //5

                    dbHelper.addExpense(amount,Reason);
                    tvTitle.setText("Expense Added");

                }else{ //6

                    dbHelper.addIncome(amount,Reason);
                    tvTitle.setText("Income Added");

                }




            }
        });



    }



    // ========================  এখন আমরা ব্যবহারকারী যদি ব্যাক বাটনে ক্লিক করে তাহলে সেই কাজটা সম্পূর্ণ করার মত মেথড টা কল করতে হবে
//code এর খালি জায়গায় mouse right button>>Generate>>Override Methods>>search করো OnBackPressed()>>ok>>Red Bulb>>Suppress GestureBackNavigation
    @SuppressLint("GestureBackNavigation") //7
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    //==========================
}

package com.arshahrear.spendmanager;
//ShowData.java
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowData extends AppCompatActivity {
    ListView listView; //1
    TextView tvTitle;
    DatabaseHelper dbHelper; //2

    ArrayList<HashMap<String, String>> arrayList ;//3
    HashMap<String, String> hashMap;

    public static boolean LoadMe=true; //4 একটা Activity তেই যাতে দুই ধরনের Data(Income,Expense) show করতে পারি তাই একটা স্ট্যাটিক ভ্যারিয়েবল ডিক্লেয়ার করা হয়েছে শর্ত দিয়ে

//true হলে Expense all data দেখাও


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);

        listView = findViewById(R.id.listView);
        tvTitle = findViewById(R.id.tvTitle);

        dbHelper = new DatabaseHelper(this);//5



        if(LoadMe==true) tvTitle.setText("Showing All Expense");
        //true হলে tvTitle এ Showing All Expense লেখা দেখাও । true তখনই হবে যখন Show all Expense এ click দিবে
        else tvTitle.setText("Showing All Income");


        loadData(); //onCreate এই loadData() সব code দেখাতে পারতাম
    }

//{{{{{{{{{{{{{{{{{{-}}}}}}}}}}}}}}}}}}}}}}}}}}}}
    //Data রাখার পুরা system টা loadData এর ভিতর রাখছি ।এর পর onCreate এ loadData() কল করেছি
    public  void loadData(){
//Cursor হলো ডাটাবেস থেকে ডাটা রিড করার জন্য একটি ইন্টারফেস।
        Cursor cursor = null;//6

        if(LoadMe==true)  cursor = dbHelper.showAllExpense();//7
        else cursor = dbHelper.showAllIncome();



        if (cursor != null && cursor.getCount() > 0) {//8

            arrayList = new ArrayList<>();//9 একটি নতুন ArrayList তৈরি করা হচ্ছে যাতে প্রতিটি ডাটাবেসের রেকর্ড রাখা হবে।

            while (cursor.moveToNext()) {//10

                //cursor.moveToNext() কল করলে:Cursor পরবর্তী রেকর্ডে চলে যায় (মানে কারেন্ট পজিশন এগোয়) loop এর মত
                //যদি নতুন রেকর্ড থাকে তবে এটি true রিটার্ন করে। রেকর্ড না থাকে (শেষ হয়ে যায়) তবে এটি false রিটার্ন করে। এইজন্য moveToNext use করি

                int id = cursor.getInt(0);
                double amount = cursor.getDouble(1);
                String reason = cursor.getString(2);
                double time = cursor.getDouble(3);


                //প্রতিটি রেকর্ডকে HashMap(key-value pair) আকারে ArrayList এ সংরক্ষণ করে,যাতে পরবর্তীতে UI বা অন্য জায়গায় ডাটা দেখানো যায়।
                hashMap = new HashMap<>();
                hashMap.put("id", ""+id);
                hashMap.put("amount", ""+amount);
                hashMap.put("reason", ""+reason);
                hashMap.put("time", ""+time);
                arrayList.add(hashMap);
            }

    //...................adapter call করছি just ।। create করছি নিচে

            listView.setAdapter(new MyAdapter()); //11

            //ListView/RecyclerView সরাসরি ডেটা বুঝে না
            //ListView বা RecyclerView হলো শুধু একটা খালি container
            //Adapter ছাড়া ListView/RecyclerView কখনোই ডেটা দেখাতে পারবে না।
            //কারণ Adapter-ই হলো সেই middle-man/bridge, যেটা ডেটাকে UI-তে রূপান্তর করে।

    //...................

        }else{

            tvTitle.append("\n No Data Found");

        }
    }

//{{{{{{{{{{{{{{{{{{-}}}}}}}}}}}}}}}}}}}}}}}}}}}}



    //👍Layout xml create করতে হবে। New>>XML>>Layout XML File >>name:item >>ok



    //------------------------------------Adapter Create

   // public class MyAdapter extends BaseAdapter {} এতটুকু লেখার পর red bulb এ click দিয়ে implement methods >> ok
    public class MyAdapter extends BaseAdapter {//11.1
        @Override
        public int getCount() {
            return arrayList.size();
            //return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflate = getLayoutInflater();//12
            View myView = inflate.inflate(R.layout.item, parent,false);

            TextView tvReason = myView.findViewById(R.id.tvReason);
            TextView tvAmount = myView.findViewById(R.id.tvAmount);
            TextView tvDelete = myView.findViewById(R.id.tvDelete);

            hashMap = arrayList.get(position); //13

            String id = hashMap.get("id");
            String amount = hashMap.get("amount");
            String reason = hashMap.get("reason");

            tvReason.setText(reason);//14
            tvAmount.setText("BDT "+amount);

//ddddddddddddddddddddddddddddddddddddddddddddddd

            //Delete button এর জন্য কাজ করবো

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //15


                    if(LoadMe==true) dbHelper.deleteExpense(id); // এতটুকু পর্যন্ত করলে data delete হবে ।
                    // but auto load হয়ে data সরে যাবে না । পুরা app থেকে বের হয়ে আবার ঢুকলে তখন দেখবো data গেছে। তাই loadData call করে দিচি
                    else dbHelper.deleteIncome(id);
                    //DatabaseHelper  এ কিন্তু deleteExpense  , deleteIncome method ধরছিলাম

                    loadData();
                }
            });
//ddddddddddddddddddddddddddddddddddddddddddddddd


            return myView;//16
        }
    }



}

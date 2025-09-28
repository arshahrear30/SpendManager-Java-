package com.arshahrear.spendmanager;

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
    ListView listView;
    TextView tvTitle;
    DatabaseHelper dbHelper;

    ArrayList<HashMap<String, String>> arrayList ;
    HashMap<String, String> hashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);
        listView = findViewById(R.id.listView);
        tvTitle = findViewById(R.id.tvTitle);
        dbHelper = new DatabaseHelper(this);



        Cursor cursor = dbHelper.showAllData();
        if (cursor != null && cursor.getCount() > 0) {


            arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                double amount = cursor.getDouble(1);
                String reason = cursor.getString(2);
                double time = cursor.getDouble(3);


                hashMap = new HashMap<>();
                hashMap.put("id", ""+id);
                hashMap.put("amount", ""+amount);
                hashMap.put("reason", ""+reason);
                hashMap.put("time", ""+time);

                arrayList.add(hashMap);
            }
//....adapter call করছি just ।। create করছি নিচে

            listView.setAdapter(new MyAdapter());

//...................

        }else{
            tvTitle.append("\n No Data Found");

        }



    }
    //Layout xml create করতে হবে। New>>XML>>Layout XML File >>name:item >>ok

    //------------------------------------Adapter Create

   // public class MyAdapter extends BaseAdapter {} এতটুকু লেখার পর red bulb এ click দিয়ে implement methods >> ok
    public class MyAdapter extends BaseAdapter {
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
            LayoutInflater inflate = getLayoutInflater();
            View myView = inflate.inflate(R.layout.item, parent,false);

            TextView tvReason = myView.findViewById(R.id.tvReason);
            TextView tvAmount = myView.findViewById(R.id.tvAmount);
            TextView tvDelete = myView.findViewById(R.id.tvDelete);

          hashMap = arrayList.get(position);

            String id = hashMap.get("id");
            String amount = hashMap.get("amount");
            String reason = hashMap.get("reason");
            String time = hashMap.get("time");

            tvReason.setText(reason);
            tvAmount.setText("BDT "+amount);

//ddddddddddddddddddddddddddddddddddddddddddddddd

            //Delete এর জন্য কাজ করবো

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dbHelper.deleteByid(id); // এতটুকু পর্যন্ত করলে data delete হবে ।
                    // but auto load হয়ে data সরে যাবে না । পুরা app থেকে বের হয়ে আবার ঢুকলে তখন দেখবো data গেছে।

                }
            });
//ddddddddddddddddddddddddddddddddddddddddddddddd


            return myView;
        }
    }



}

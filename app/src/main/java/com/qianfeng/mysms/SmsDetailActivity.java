package com.qianfeng.mysms;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SmsDetailActivity extends AppCompatActivity{

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        initView();
        intiData(address);
    }

    private void initView(){
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new MyAdapter(this, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    private void intiData(String address){
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = getContentResolver().query(uri, null, "address=?", new String[]{address}, null);
        adapter.changeCursor(cursor);
    }

    class MyAdapter extends CursorAdapter{

        public MyAdapter(Context context, Cursor c, int flags){
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            if(type == 1){
                return getLayoutInflater().inflate(R.layout.left_layout, parent, false);
            }else{
                return getLayoutInflater().inflate(R.layout.right_layout, parent, false);
            }
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor){
            TextView tvContent = (TextView) view.findViewById(R.id.content);
            tvContent.setText(cursor.getString(cursor.getColumnIndex("body")));
        }


    }
}

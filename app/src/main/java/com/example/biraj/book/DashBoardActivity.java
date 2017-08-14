package com.example.biraj.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Intent i = getIntent();
        String title = i.getStringExtra("menu");
        setTitle(title);
        ListView listDashItem = (ListView)findViewById(R.id.listDashboard);

       // final String[] values = new String[]{"item1", "item2", "item3",
         //       "item4", "item5"};
        ArrayList<String> values = new ArrayList<String>();
        values.add("item1");
        values.add("item2");
        values.add("item3");
        values.add("item4");
        values.add("item5");

        /*Integer imageid[] = {
                R.mipmap.ok,
                R.mipmap.ok,
                R.mipmap.ok,
                R.mipmap.no,
                R.mipmap.no

        };*/

        ListView userDashList = (ListView) findViewById(R.id.listDashboard);
        DashBoardMenuAdapter menuAdapter = new DashBoardMenuAdapter(this, R.layout.activity_dash_board, values);
        listDashItem.setAdapter(menuAdapter);
    }
}

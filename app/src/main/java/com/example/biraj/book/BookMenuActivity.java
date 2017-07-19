package com.example.biraj.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class BookMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        //TextView textView = (TextView) findViewById(R.id.textViewWelocome);
        Intent bookIntent = getIntent();
        //TODO remove the comment
        String userName = bookIntent.getStringExtra("loginEmail");
        //textView.setText("Welcome! "+userName);

        super.setTitle(userName);

        final String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2"};

        Integer imageid[] = {
                R.mipmap.ok,
                R.mipmap.ok,
                R.mipmap.ok,
                R.mipmap.ok,
                R.mipmap.ok,
                R.mipmap.no,
                R.mipmap.no,
                R.mipmap.no,
                R.mipmap.no,
                R.mipmap.no,
        };

        ListView listView = (ListView) findViewById(R.id.menuList);
        listView.setAdapter(new CustomListAdapter(this, values, imageid));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"You Clicked "+values[i],Toast.LENGTH_SHORT).show();
            }
        });
        //listView.setListAdapter(new MyListviewAdapter(this, values));


    }

}

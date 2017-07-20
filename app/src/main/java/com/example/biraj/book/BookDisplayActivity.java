package com.example.biraj.book;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookDisplayActivity extends AppCompatActivity {
    private GridView applicationMenuGridView;
    ApplicationMenuAdapter menuAdapter;
    ArrayList<String> menulist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_display);
        menulist.add("add");
        menulist.add("list");
        menulist.add("view log");
        menulist.add("help");
        menulist.add("about");
        applicationMenuGridView = (GridView) findViewById(R.id.menuGrid);
        menuAdapter = new ApplicationMenuAdapter(this, R.layout.activity_book_display, menulist);
        applicationMenuGridView.setAdapter(menuAdapter);
        applicationMenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Toast.makeText(getBaseContext(), "You touched on "+menulist.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

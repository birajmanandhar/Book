package com.example.biraj.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class UserViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        setTitle("User Page");

        final String[] values = new String[]{"Dashboard", "Add Book", "Edit Book",
                "Account Setting", "Ordered Books"};

        Integer imageid[] = {
                R.mipmap.ok,
                R.mipmap.ok,
                R.mipmap.ok,
                R.mipmap.no,
                R.mipmap.no

        };

        ListView userDashList = (ListView) findViewById(R.id.userViewList);
        userDashList.setAdapter(new CustomListAdapter(this, values, imageid));

        userDashList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String menuItemName = values[i];

                switch (menuItemName) {
                    case "Dashboard":
                        Intent iDashBoard = new Intent(UserViewActivity.this, DashBoardActivity.class);
                        iDashBoard.putExtra("menu", "Dashboard");
                        startActivity(iDashBoard);
                        break;
                    case "Add Book":
                        Intent iAddBook = new Intent(UserViewActivity.this, AddBookActivity.class);
                        iAddBook.putExtra("menu", "Add Book");
                        startActivity(iAddBook);
                        break;
                    case "Edit Book":
                        Intent iEditBook = new Intent(UserViewActivity.this, AddBookActivity.class);
                        iEditBook.putExtra("menu", "Edit Book");
                        startActivity(iEditBook);
                        break;
                    case "Account Setting":
                        Toast.makeText(UserViewActivity.this, "Clicked on"+menuItemName,Toast.LENGTH_SHORT).show();
                        break;
                    case "Ordered Books":
                        Toast.makeText(UserViewActivity.this, "Clicked on"+menuItemName,Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Log.d("menu item err","Miss fires!");
                        break;
                }
            }
        });


    }

}

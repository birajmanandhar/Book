package com.example.biraj.book;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class BookDisplayActivity extends AppCompatActivity {
    private GridView applicationMenuGridView;
    ApplicationMenuAdapter menuAdapter;
    ArrayList<String> menulist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_display);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                //do your work
            } else {
                requestPermission();
            }
        }else{
            //For lower versions below marshmallow
            createUserFiles();
        }
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
                Toast.makeText(getBaseContext(), "You touched on " + menulist.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    protected void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createUserFiles();
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void createUserFiles() {
        try {
            String path = Environment.getExternalStorageDirectory().toString();
            Log.d("Biraj message path :: ", path);
            String sep = "/";
            String directoryName = "MeroKhata";
            String directoryFullPath = path + sep + directoryName;
            final File newFile = new File(directoryFullPath);
            boolean isFolderCreated = false;
            if (!newFile.exists()) {
                isFolderCreated = newFile.mkdir();
            }
            if (isFolderCreated) {
                String fileName = "datafiles.json";
                String fileFullPath = directoryFullPath + sep + fileName;
                File file = new File(fileFullPath);
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

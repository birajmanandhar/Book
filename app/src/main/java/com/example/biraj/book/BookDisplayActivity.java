package com.example.biraj.book;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class BookDisplayActivity extends AppCompatActivity {
    private GridView applicationMenuGridView;
    ApplicationMenuAdapter menuAdapter;
    //ArrayList<String> menulist = new ArrayList<>();
    ArrayList<BookInfo> bookInfo = new ArrayList<BookInfo>();
    ImageView imageView;

    //hardcoded for trial picasso
    public static String[] eatFoodyImages = {
            "http://i.imgur.com/rFLNqWI.jpg",
            "http://i.imgur.com/C9pBVt7.jpg",
            "http://i.imgur.com/rT5vXE1.jpg",
            "http://i.imgur.com/aIy5R2k.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/syELajx.jpg",
            "http://i.imgur.com/COzBnru.jpg",
            "http://i.imgur.com/Z3QjilA.jpg",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_display);
        createUserInformation();
        loadBookInfoModel();
        loadApplicationMenuAdapter();

        //*****Trail with piasco******
        /*
        setContentView(R.layout.trial_layout);
        imageView = (ImageView) findViewById(R.id.imageView);
        //Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).load("https://cms-assets.tutsplus.com/uploads/users/21/posts/19431/featured_image/CodeFeature.jpg").placeholder(R.mipmap.alert_icon).error(R.mipmap.alert_icon).into(imageView);
        */

    }

    public void createUserInformation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                //do your work
            } else {
                requestPermission();
            }
        } else {
            //For lower versions below marshmallow
            createUserFiles();
        }
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
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 100);
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
                String fileName = "BookData.json";
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

    public void loadBookInfoModel() {
        try {
            String resultData = FileReader.getStringFromFile(FileReader.bookInfoDataPath);

            JSONObject jObject = new JSONObject(resultData);
            JSONArray jsonBook = jObject.getJSONArray("jsonbook");
            String resultDataJsonBook = jsonBook.toString();

            Type arrayListType = new TypeToken<ArrayList<BookInfo>>() {
            }.getType();
            Gson g = new Gson();
            bookInfo = g.fromJson(resultDataJsonBook, arrayListType);

            for (int i = 0; i < bookInfo.size(); i++) {
                String bookName = bookInfo.get(i).getBookName();
                //String bookUploadDate = bookInfo.get(i).getBookUploadDate();
                Log.i("book name i:" + i + " ", bookName);
                //Log.i("book date i:" + i + " ", bookUploadDate);
            }
        } catch (Exception e) {
            Log.d("loadBookModel:", e.getMessage());
        }
    }

    public void loadApplicationMenuAdapter() {
        applicationMenuGridView = (GridView) findViewById(R.id.menuGrid);
        this.runOnUiThread(new Runnable() {
            public void run() {
                menuAdapter = new ApplicationMenuAdapter(BookDisplayActivity.this, R.layout.activity_book_display, bookInfo);
                applicationMenuGridView.setAdapter(menuAdapter);
            }
        });

        applicationMenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Toast.makeText(getBaseContext(), "Book by : " + bookInfo.get(position).getBookAuthor(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.biraj.book;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


@SuppressWarnings("ResourceType")
public class BookDisplayActivity extends AppCompatActivity {
    ImageView imageView;
    public static final Integer NOTIFICATION_ID = 104;
    //private static final String TAG_BOOK_DISPLAY_FRAGMENT = "BookDisplayFragment";
    private BookDisplayFragment bookDisplayFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Testing the Sliding Menu

        //*****Trial with slide menu******
        setContentView(R.layout.trial_sliding_layout);
        setTitle("Main Menu");
        setupSlidingMenu();

        createUserInformation();
        // find the retained fragment on activity restarts
        FragmentManager mFragmentManager;
        if (savedInstanceState == null) {
            // only create fragment if activity is started for the first time
            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            Fragment bookDisplayFragment = new BookDisplayFragment();
            fragmentTransaction.replace(R.id.main_content, bookDisplayFragment, bookDisplayFragment.getClass().getSimpleName()).commit();
        } else {
            // do nothing - fragment is recreated automatically
        }


        /*
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment bookDisplayFragment = new BookDisplayFragment();
        ft.replace(R.id.main_content, bookDisplayFragment, bookDisplayFragment.getClass().getSimpleName()).commit();*/
        //.addToBackStack(null)

        //TODO For Notification
        //setupNotification();

        //*****Trail with piasco******
        /*
        setContentView(R.layout.trial_layout);
        imageView = (ImageView) findViewById(R.id.imageView);
        //Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).load("https://cms-assets.tutsplus.com/uploads/users/21/posts/19431/featured_image/CodeFeature.jpg").placeholder(R.mipmap.alert_icon).error(R.mipmap.alert_icon).into(imageView);
        */

    }

    /*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

        }
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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


    public void setupNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.book)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, BookDisplayActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(BookDisplayActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mNotificationId is a unique integer your app uses to identify the
        // notification. For example, to cancel the notification, you can pass its ID
        // number to NotificationManager.cancel().
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

    public void setupSlidingMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setNavigationSettings();
    }

    public void setNavigationSettings(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    try {
                        Fragment helpFragment = new HelpFragment();
                        if (helpFragment != null) {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            Fragment bookDisplayFragment = new BookDisplayFragment();
                            ft.replace(R.id.main_content, bookDisplayFragment, bookDisplayFragment.getClass().getSimpleName()).commit();
                        }
                    } catch (Exception e) {
                        Log.d("Home btn Err:", e.getMessage());
                    }
                } else if (id == R.id.nav_log_register) {
                    try {
                        Fragment loginFragment = new LoginFragment();
                        if (loginFragment != null) {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.main_content, loginFragment, loginFragment.getClass().getSimpleName()).commit();
                        }
                    } catch (Exception e) {
                        Log.d("Reg btn Err:", e.getMessage());
                    }
                }else if (id == R.id.nav_about) {
                    try {
                        Fragment aboutFragment = new BlankFragment();
                        if (aboutFragment != null) {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.main_content, aboutFragment, aboutFragment.getClass().getSimpleName()).commit();
                        }
                    } catch (Exception e) {
                        Log.d("About btn Err:", e.getMessage());
                    }
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}

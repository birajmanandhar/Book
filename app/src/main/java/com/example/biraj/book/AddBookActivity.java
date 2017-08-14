package com.example.biraj.book;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class AddBookActivity extends AppCompatActivity {

    final public static int PICK_IMAGE = 105;
    ImageView imageView;
    Uri chosenImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Intent intent = getIntent();
        String menu = intent.getStringExtra("menu");
        if(menu.equals("Add Book")){
            setTitle("Add Book");
        } else if (menu.equals("Edit Book")){
            setTitle("Edit Book");
        }
        Button btnPickImage = (Button) findViewById(R.id.btnImagePick);
        imageView = (ImageView) findViewById(R.id.imageViewBookPick);
        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        Button btnBookPost = (Button) findViewById(R.id.btnBookPost);
        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void onResume() {
        super.onResume();
        if (imageView != null) {
            if (chosenImageUri != null) {
                try {
                    Picasso.with(getApplicationContext())
                            .load(chosenImageUri)
                            .resize(600, 600)
                            .centerCrop()
                            .into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            //Method using picass0
            chosenImageUri = data.getData();
            String filePath = getRealPathFromURI(this, chosenImageUri);

            Log.d("Biraj filePath: ", filePath);

            Picasso.with(getApplicationContext())
                    .load(chosenImageUri)
                    .resize(600, 600)
                    .centerCrop()
                    .into(imageView);


            /*Getting path from URI two option below
            String path = yourAndroidURI.uri.getPath() // "file:///mnt/sdcard/FileName.mp3"
            File file = new File(new URI(path));

            or

            String path = yourAndroidURI.uri.toString() // "/mnt/sdcard/FileName.mp3"
            File file = new File(new URI(path));*/


            /* Method without picasso
            Uri chosenImageUri = data.getData();
            Bitmap mBitmap = null;
            Uri uri; // the URI you've received from the other app
            InputStream in = null;
            try {
                in = getContentResolver().openInputStream(chosenImageUri);
                ExifInterface exifInterface = new ExifInterface(in);
                // Now you can extract any Exif tag you want
                // Assuming the image is a JPEG or supported raw format
                int rotation = 0;
                int orientation = exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotation = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotation = 270;
                        break;
                }

                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(mBitmap, 600, 600, true);

                Matrix matrix = new Matrix();
                if (rotation != 0f) {
                    matrix.preRotate(rotation);
                }
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

                //mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                imageView.setImageBitmap(Bitmap.createScaledBitmap(rotatedBitmap, rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), true));

            } catch (IOException e) {
                // Handle any errors
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ignored) {
                    }
                }
            }
            */
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        if (chosenImageUri != null) {
            savedInstanceState.putParcelable("image", chosenImageUri);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Uri savedImageUri;
        savedImageUri = savedInstanceState.getParcelable("image");
        if (savedImageUri != null) {
            chosenImageUri = savedImageUri;
        }
    }



    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    class UploadBookTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        public UploadBookTask(RegisterActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Uploading book info, please wait....");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {

            SharedPreferences.Editor editor = getSharedPreferences("DataStore", MODE_PRIVATE).edit();
            editor.putString("jsondata", result);
            editor.commit();
            editor.apply();
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            finish();
        }

        @Override
        protected String doInBackground(String... params) {
            String data = params[0];
            return data;
        }

    }


}

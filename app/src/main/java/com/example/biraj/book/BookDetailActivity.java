package com.example.biraj.book;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        String serialKey = getIntent().getStringExtra("serialKey");
        BookInfo bookInfoRecord = (BookInfo) getIntent().getSerializableExtra(serialKey);
        ImageView imgView = (ImageView) findViewById(R.id.bookImage);
        Picasso.with(this).load(bookInfoRecord.getUrl()).
                placeholder(R.mipmap.alert_icon).
                error(R.mipmap.alert_icon).
                into(imgView);
        TextView bookName = (TextView) findViewById(R.id.bookName);
        TextView bookAuthor = (TextView) findViewById(R.id.bookAuthor);
        TextView bookUserName = (TextView) findViewById(R.id.bookUserName);
        TextView bookPrice = (TextView) findViewById(R.id.bookPrice);
        TextView bookPublishedDate = (TextView) findViewById(R.id.bookPublishedDate);

        bookName.setText(bookInfoRecord.getBookName());
        bookAuthor.setText(bookInfoRecord.getBookAuthor());
        //bookUserName.setText(bookInfoRecord.getUserId());
        //bookPrice.setText(bookInfoRecord.getBookPrice());
        bookUserName.setText("NA");
        bookPrice.setText("NA");
        bookPublishedDate.setText(bookInfoRecord.getBookUploadDate());


    }
}

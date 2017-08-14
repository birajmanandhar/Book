package com.example.biraj.book;


import java.io.Serializable;

/**
 * Created by Biraj on 7/20/2017.
 */

public class BookInfo implements Serializable{
    String bookId;
    String bookName;
    String bookAuthor;
    String bookUploadDate;
    String bookImagePath;
    String url;

    @Override
    public String toString() {
        return bookId + " - " + bookName + " - " + bookAuthor + " - " + bookUploadDate + " - " + bookImagePath + " - " + url;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookUploadDate() {
        return bookUploadDate;
    }

    public String getBookImagePath() {
        return bookImagePath;
    }

    public String getUrl() {
        return url;
    }
}

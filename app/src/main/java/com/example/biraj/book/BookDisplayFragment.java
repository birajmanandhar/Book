package com.example.biraj.book;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BookDisplayFragment extends Fragment {
    private GridView applicationMenuGridView;
    ApplicationMenuAdapter menuAdapter;
    ArrayList<BookInfo> bookInfo = new ArrayList<BookInfo>();
    Context contextBookDisplay;
    View rootView;
    public static final String SER_KEY = "key.for.serializable";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            contextBookDisplay = context;
        } catch (Exception e) {
            Log.d("Exception",e.getMessage());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_book_display, container, false);
        loadBookInfoModel();
        loadApplicationMenuAdapter();
        return rootView;
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
        applicationMenuGridView = (GridView) rootView.findViewById(R.id.menuGrid);
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                menuAdapter = new ApplicationMenuAdapter(contextBookDisplay, R.layout.fragment_book_display, bookInfo);
                applicationMenuGridView.setAdapter(menuAdapter);
            }
        });

        applicationMenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                //public void SerializeMethod(){}
                BookInfo passBookInfoRow = bookInfo.get(position);
                Intent mIntent = new Intent(contextBookDisplay, BookDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("serialKey", SER_KEY);
                mBundle.putSerializable(SER_KEY, passBookInfoRow);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                //Toast.makeText(getBaseContext(), "Book by : " + bookInfo.get(position).getBookAuthor(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

/*
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
*/
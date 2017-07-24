package com.example.biraj.book;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/* Adapter class for setting the grid menu list in the application */

public class ApplicationMenuAdapter extends ArrayAdapter<BookInfo> {

    private Context mContext;
    ImageView menuImageView;
    TextView labelView;
    ArrayList<BookInfo> lists;

    public ApplicationMenuAdapter(Context context, int textViewResourceId, ArrayList<BookInfo> objects) {
        super(context, textViewResourceId, objects);
        mContext = context;
        lists = objects;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.application_menu_row_layout, null, false);
        }
        menuImageView = (ImageView) v.findViewById(R.id.menuImageView);
        menuImageView.setAdjustViewBounds(true);
        labelView = (TextView) v.findViewById(R.id.menuName);

        final String applicationMenu = lists.get(position).getBookName();
        //final String resource = lists.get(position).getBookAuthor();

        try {
            if (applicationMenu != null) {
                if (applicationMenu != null && applicationMenu.length() > 0) {
                    // Checks for the item name and sets respective image for
                    // the item name
                    //skip code test
                    //final String resource = FileReader.userImageFolderPath+FileReader.sep+(lists.get(position).getBookName())+".png";
                    try {
                        // Sets the targeted image resource or else sets android
                        // default icon
                        //skip code test
                        //Bitmap bmp = BitmapFactory.decodeFile(resource);
                        //menuImageView.setImageBitmap(bmp);
                        Picasso.with(mContext).load(lists.get(position).getUrl()).
                                fit().
                                placeholder(R.mipmap.alert_icon).
                                error(R.mipmap.alert_icon).
                                into(menuImageView);
                        labelView.setText(applicationMenu);
                    } catch (Exception ex) {
                        Log.d("Biraj I",ex.getMessage());
                        menuImageView.setImageResource(R.mipmap.alert_icon);
                    } catch (Error er) {
                        Log.d("Biraj I",er.getMessage());
                        menuImageView.setImageResource(R.mipmap.alert_icon);
                    }
                } else {
                    menuImageView.setImageResource(R.mipmap.alert_icon);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Error er) {
            er.printStackTrace();
        }
        return v;
    }

}

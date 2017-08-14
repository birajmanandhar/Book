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

/**
 * Created by Biraj on 8/4/2017.
 */

public class DashBoardMenuAdapter extends ArrayAdapter<String> {

    private Context mContext;
    ImageView menuImageView;
    TextView labelView;
    ArrayList<String> lists;

    public DashBoardMenuAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        mContext = context;
        lists = objects;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.dashboard_item, null, false);
        }
        menuImageView = (ImageView) v.findViewById(R.id.imgBook);
        menuImageView.setAdjustViewBounds(true);
        labelView = (TextView) v.findViewById(R.id.txtBookName);

        final String applicationMenu = lists.get(position).toString();
        //final String resource = lists.get(position).getBookAuthor();

        try {
            if (applicationMenu != null) {
                if (applicationMenu != null && applicationMenu.length() > 0) {
                    try {
                        Picasso.with(mContext).load(R.mipmap.wall_pic).
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
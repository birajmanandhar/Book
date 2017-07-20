package com.example.biraj.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/* Adapter class for setting the grid menu list in the application */

public class ApplicationMenuAdapter extends ArrayAdapter<String> {

    private Context mContext;
    ImageView menuImageView;
    TextView labelView;
    ArrayList<String> lists;

    public ApplicationMenuAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
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

        final String applicationMenu = lists.get(position);

        try {
            if (applicationMenu != null) {
                if (applicationMenu != null && applicationMenu.length() > 0) {
                    // Checks for the item name and sets respective image for
                    // the item name
                    int resId = 0;
                    if (applicationMenu.equalsIgnoreCase("add")) {
                        resId = R.mipmap.alert_icon;
                    } else if (applicationMenu.equalsIgnoreCase("list")) {
                        resId = R.mipmap.alert_icon;
                    } else if (applicationMenu.equalsIgnoreCase("view log")) {
                        resId = R.mipmap.alert_icon;
                    } else if (applicationMenu.equalsIgnoreCase("help")) {
                        resId = R.mipmap.alert_icon;
                    } else if (applicationMenu.equalsIgnoreCase("about")) {
                        resId = R.mipmap.alert_icon;
                    }
                    try {
                        // Sets the targeted image resource or else sets android
                        // default icon
                        menuImageView.setImageResource(resId);
                        labelView.setText(applicationMenu+" over loading the text please look out");
                    } catch (Exception ex) {
                        menuImageView.setImageResource(R.mipmap.alert_icon);
                    } catch (Error er) {
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

package com.example.biraj.book;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Biraj on 7/17/2017.
 */

public class CustomListAdapter extends ArrayAdapter<String> {
    private String[] names;
    //private String[] desc;
    private Integer[] imageid;
    private Activity context;

    public CustomListAdapter(Activity context, String[] names, Integer[] imageid) {
        super(context, R.layout.row_layout, names);
        this.context = context;
        this.names = names;
        //this.desc = desc;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.row_layout, null, true);
        TextView textViewLabel = (TextView) listViewItem.findViewById(R.id.listLabel);
        //TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        //TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.listIcon);

        textViewLabel.setText(names[position]);
        //textViewName.setText(names[position]);
        //textViewDesc.setText(desc[position]);
        image.setImageResource(imageid[position]);
        return listViewItem;
    }
}

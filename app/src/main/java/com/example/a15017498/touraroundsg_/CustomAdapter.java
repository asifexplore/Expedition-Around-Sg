package com.example.a15017498.touraroundsg_;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15017498 on 8/1/2017.
 */

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<RowItem> toDoList;

    public CustomAdapter(Context context, int resource, ArrayList<RowItem> objects) {

        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        toDoList = objects;


    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.Title);
        TextView tvDetail = (TextView) rowView.findViewById(R.id.Desc);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);


        RowItem currentItem = toDoList.get(position);


        tvTitle.setText(currentItem.getTitle());
        tvDetail.setText(currentItem.getDesc());

        if (currentItem.getTitle() == "SINGAPORE FLYER") {
            imageView.setImageResource(R.drawable.flyer_icon);
        } else {

        }


        return rowView;

    }


}
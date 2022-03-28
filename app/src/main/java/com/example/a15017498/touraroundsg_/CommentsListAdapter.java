package com.example.a15017498.touraroundsg_;

import android.content.Context;
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
 * Created by 15017498 on 9/1/2017.
 */

public class CommentsListAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Comments> mCommentsList;

    //Constructor


    public CommentsListAdapter(Context context, ArrayList<Comments> mCommentsList) {
        this.context = context;
        this.mCommentsList = mCommentsList;
    }



    @Override
    public int getCount() {
        return mCommentsList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCommentsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context,R.layout.comment_list,null);
        TextView tvName = (TextView) v.findViewById(R.id.commentName);
        TextView tvDesc = (TextView) v.findViewById(R.id.commentDesc);

        //Set Text For textview
        tvName.setText(mCommentsList.get(i).getName());
        tvDesc.setText(mCommentsList.get(i).getComment_given());

        v.setTag(mCommentsList.get(i).getId());

        return v;
    }
}

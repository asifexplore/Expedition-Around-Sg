package com.example.a15017498.touraroundsg_;

/**
 * Created by 15017498 on 8/1/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class tab1 extends Fragment {

    ListView lv;
    CustomAdapter customAdapter = null;
    ArrayList<RowItem> ToDoList = new ArrayList<RowItem>();
    Button btnADD;
    ImageView imageView;
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);

        btnADD = (Button) rootView.findViewById(R.id.btnADD);
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), post_blog.class);
                startActivity(intent);
            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mBlogList = (RecyclerView) rootView.findViewById(R.id.blog_list1);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));



        final RowItem flyer = new RowItem("SINGAPORE FLYER", "Welcome to Singapore Flyer");

        customAdapter = new CustomAdapter(getActivity(), R.layout.row, ToDoList);

        ToDoList.add(flyer);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<BlogDeveloper, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogDeveloper, BlogViewHolder>(BlogDeveloper.class, R.layout.blog_row, BlogViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, BlogDeveloper model, int position) {
               final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getActivity().getApplicationContext(),model.getPost_image());
                imageView = (ImageView) viewHolder.mView.findViewById(R.id.imageViewDeveloperfker);
                String post_name = model.getPost_name();

                if(post_name.equalsIgnoreCase("flyer")){

                    imageView.setImageResource(R.drawable.sg_flyer);
                }

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(getActivity().getApplicationContext(), post_key, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(), flyer.class);
                        intent.putExtra("blog_id",post_key);
                        startActivity(intent);
                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.textView3);
            post_title.setText(title);
        }

        public void setDesc(String Desc) {
            TextView post_Desc = (TextView) mView.findViewById(R.id.textView4);
            post_Desc.setText(Desc);
        }

        public void setImage(Context ctx,String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.imageViewDeveloperfker);
            Picasso.with(ctx).load(image).into(post_image);
        }

    }

}

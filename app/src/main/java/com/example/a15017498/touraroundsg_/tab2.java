package com.example.a15017498.touraroundsg_;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by 15017498 on 8/1/2017.
 */

public class tab2 extends Fragment {

    ListView lv;
    CustomAdapter customAdapter = null;
    ArrayList<RowItem> ToDoList = new ArrayList<RowItem>();
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseFav;
    ImageView imageView;
    private FirebaseAuth mAuth;
    String find;
    private DatabaseReference mCurrentUser;
    private Query mQueryCurrentUser;
    TextView textViewTitleRow2;
    TextView textViewDescRow2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabaseFav = FirebaseDatabase.getInstance().getReference().child("Fav");
        mBlogList = (RecyclerView) rootView.findViewById(R.id.blog_list2);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAuth = FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }else {
            String current_userId = mAuth.getCurrentUser().getUid();
            mCurrentUser = FirebaseDatabase.getInstance().getReference().child("Fav").child(current_userId);
            mQueryCurrentUser = mCurrentUser.orderByChild("uid").equalTo("true");
        }

            //Toast.makeText(getActivity().getApplicationContext(), user_id, Toast.LENGTH_SHORT).show();
            return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
        FirebaseRecyclerAdapter<BlogDeveloper, tab2.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogDeveloper, tab2.BlogViewHolder>(BlogDeveloper.class, R.layout.blog_row2, tab2.BlogViewHolder.class, mQueryCurrentUser) {
            @Override
            protected void populateViewHolder(final tab2.BlogViewHolder viewHolder, final BlogDeveloper model, int position) {
                final String post_key = getRef(position).getKey();

                final String asif = mDatabaseFav.child(mAuth.getCurrentUser().getUid()).getKey();
            //     Toast.makeText(getActivity().getApplicationContext(),model.getUid(), Toast.LENGTH_LONG).show();
                // String fav =  mDatabaseFav.child(post_key).child(model.getUid());
                mDatabaseFav.child(mAuth.getCurrentUser().getUid()).child(post_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (mAuth.getCurrentUser().getUid().equalsIgnoreCase(asif)) {
                            //  Toast.makeText(getActivity().getApplicationContext(), dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();

                          //  Toast.makeText(getActivity().getApplicationContext(),dataSnapshot+"", Toast.LENGTH_LONG).show();
                            Log.d(TAG, post_key + "");
                            find = dataSnapshot.child("uid").getValue(String.class);
                            //Toast.makeText(getActivity().getApplicationContext(), find, Toast.LENGTH_LONG).show();
                            if (find != null && find.equalsIgnoreCase("true")) {
                                viewHolder.setTitle(model.getTitle());
                                viewHolder.setDesc(model.getDesc());
                               // viewHolder.setImage(getActivity().getApplicationContext(), model.getPost_image());

//                                textViewTitleRow2 = (TextView) viewHolder.mView.findViewById(R.id.textViewTitleRow2);
//                                textViewTitleRow2.setText(model.getTitle());
//
//                                textViewDescRow2 = (TextView) viewHolder.mView.findViewById(R.id.textViewDescRow2);
//                                textViewDescRow2.setText(model.getLocation_desc());
//
//                                imageView = (ImageView) viewHolder.mView.findViewById(R.id.imageViewRow2);
//                                String post_name = model.getPost_name();

                             //   imageView.setImageResource(R.drawable.sg_flyer);
//                                if (post_name.equalsIgnoreCase("flyer")) {
//                                    imageView.setImageResource(R.drawable.sg_flyer);
//                                }
                            } else {

                            }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (find != null && find.equalsIgnoreCase("true")) {
                            viewHolder.setTitle(dataSnapshot.child("title").getValue().toString());
                            viewHolder.setDesc(dataSnapshot.child("desc").getValue().toString());
                        viewHolder.setImage(getActivity().getApplicationContext(), dataSnapshot.child("post_image").getValue().toString());
                          // Toast.makeText(getActivity().getApplicationContext(), dataSnapshot+"", Toast.LENGTH_LONG).show();
                        }else{

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                if (find != null && find.equalsIgnoreCase("true")) {
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //  Toast.makeText(getActivity().getApplicationContext(), post_key, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity().getApplicationContext(), flyer.class);
                            intent.putExtra("blog_id", post_key);
                            startActivity(intent);
                        }
                    });
                } else {

                }

            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }else{

        }}


    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.textViewTitleRow2);
            post_title.setText(title);
        }

        public void setDesc(String Desc) {
            TextView post_Desc = (TextView) mView.findViewById(R.id.textViewDescRow2);
            post_Desc.setText(Desc);
        }

        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.imageViewRow2);
            Picasso.with(ctx).load(image).into(post_image);

        }

    }
}

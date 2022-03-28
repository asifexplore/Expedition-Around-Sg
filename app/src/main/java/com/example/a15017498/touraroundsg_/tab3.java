package com.example.a15017498.touraroundsg_;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab3 extends Fragment {

    private RecyclerView mBlogListPersonal;
    private DatabaseReference mDatabase;
    ImageView imageView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseCurrentUser;
    String find;
    private Query mQueryCurrentUser;

    public tab3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.tab3, container, false);

        mBlogListPersonal = (RecyclerView) rootView.findViewById(R.id.blog_listPersonal);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("personal_blog");
        mBlogListPersonal.setHasFixedSize(true);
        mBlogListPersonal.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();
        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("personal_blog");
        mQueryCurrentUser =mDatabaseCurrentUser.orderByChild("user_id").equalTo(currentUserId);



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<BlogPersonal, tab3.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogPersonal, tab3.BlogViewHolder>(BlogPersonal.class, R.layout.blog_row3, tab3.BlogViewHolder.class, mQueryCurrentUser) {
            @Override
            protected void populateViewHolder(tab3.BlogViewHolder viewHolder, BlogPersonal model, int position) {
                final String post_key = getRef(position).getKey();
                final String asif = model.getUser_id();

                if(mAuth.getCurrentUser().getUid().equalsIgnoreCase(asif) && asif != null) {
                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setDesc(model.getDesc());
                    viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());

                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getActivity().getApplicationContext(), post_key, Toast.LENGTH_LONG).show();
                            Intent intentPersonal = new Intent(getActivity().getApplicationContext(), personalFlyer.class);
                            intentPersonal.putExtra("blog_id", post_key);
                            startActivity(intentPersonal);
                        }
                    });
                }else{

                }

            }
        };


        mBlogListPersonal.setAdapter(firebaseRecyclerAdapter);

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.textViewRow3);
            post_title.setText(title);
        }

        public void setDesc(String Desc) {
            TextView post_Desc = (TextView) mView.findViewById(R.id.textViewRow4);
            post_Desc.setText(Desc);
        }

        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.imageViewRow3);
            Picasso.with(ctx).load(image).into(post_image);

        }


    }

}

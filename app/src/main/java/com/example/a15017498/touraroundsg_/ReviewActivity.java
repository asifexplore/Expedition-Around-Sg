package com.example.a15017498.touraroundsg_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = "";
    Button btnComment;
    EditText editTextComment;


    ListView lv;
    CommentsListAdapter adapter;
    ArrayList<Comments> mCommentsList;
    String post_key;

    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private RecyclerView mBlogList;
    private DatabaseReference mDatabaseUsers;
    private String contact_noo;

    private Query mQueryCurrentUser;
    private DatabaseReference mCurrentComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);



        btnComment = (Button) findViewById(R.id.btnComment);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
//        lv = (ListView) findViewById(R.id.ReviewLayout);
        mCommentsList = new ArrayList<>();
        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));

        Intent intentReceived4=getIntent();
          contact_noo = intentReceived4.getStringExtra("post_key");

        mCurrentComment = FirebaseDatabase.getInstance().getReference().child("Comments");

        //Toast.makeText(this, mCurrentComment+"", Toast.LENGTH_SHORT).show();
        mQueryCurrentUser = mCurrentComment.orderByChild("post_key").equalTo(contact_noo);


//        //Adding Sample Data for ListView
//        mCommentsList.add(new Comments(1, "asif", "Awesome"));
//        adapter = new CommentsListAdapter(getApplicationContext(), mCommentsList);
//        // lv.setAdapter(adapter);

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//         post_key = prefs.getString("post_key","Default no key in reviewActivity");



        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Comments");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());


        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
                editTextComment.setText("");
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class, R.layout.comment_row, BlogViewHolder.class, mQueryCurrentUser
        ) {

            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                //Toast.makeText(ReviewActivity.this, model.getPost_key(), Toast.LENGTH_LONG).show();
                if(model.getPost_key().equalsIgnoreCase(contact_noo)){
                    viewHolder.setTitle(model.getU_id());
                    viewHolder.setDesc(model.getComment());
                    viewHolder.setUsername(model.getUsername());
                }
                else{

                }
                  //  mDatabase.child(post_key);
                   // Toast.makeText(ReviewActivity.this, post_key, Toast.LENGTH_LONG).show();

                    //Username Overriding post_title Textfield


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
            TextView pos_name = (TextView) mView.findViewById(R.id.post_title);
            pos_name.setText(title);

        }

        public void setDesc(String desc) {
            TextView pos_comment = (TextView) mView.findViewById(R.id.post_text);
            pos_comment.setText(desc);
        }

        public void setUsername(String username) {
            TextView post_username = (TextView) mView.findViewById(R.id.post_title);
            post_username.setText(username);
        }

    }

    private void startPosting() {
        mProgress.setMessage("POSTING TO DATABASE.....");
        mProgress.show();
        final String comment = editTextComment.getText().toString().trim();

        if (!TextUtils.isEmpty(comment)) {
            //FROM NOW POST ONTO DATABASE

            final DatabaseReference newPost = mDatabase.push();

            final String user_id = mAuth.getCurrentUser().getUid();

            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPost.child("comment").setValue(comment);
                    newPost.child("u_id").setValue(user_id);
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue());
                    newPost.child("post_key").setValue(contact_noo);
                   // newPost.child("post_key").setValue(post_key);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });


            mProgress.dismiss();
        }

    }


}

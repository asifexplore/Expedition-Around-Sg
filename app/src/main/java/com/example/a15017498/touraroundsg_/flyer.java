package com.example.a15017498.touraroundsg_;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class flyer extends AppCompatActivity {

    TextView tvTitle;
    TextView tvDesc;
    ImageButton img;
    Button btnReviews;
    ImageButton btnFav;
//    ImageView imageView2;

    private DatabaseReference mDatabaseFav;
    private DatabaseReference mDatabase;
    private boolean mProcessFav = false;
    private String strStatus = "N";
    private Boolean mProcessLike = false;
    String find = "";
    private String mPost_key = null;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ImageView imageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flyer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        getSupportActionBar().setTitle("HOTSPOTS");

        tvTitle = (TextView) findViewById(R.id.TitleT);
        tvDesc = (TextView) findViewById(R.id.DescD);

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        img = (ImageButton) findViewById(R.id.imageButton);
        btnReviews = (Button) findViewById(R.id.btnReviews);
        btnFav = (ImageButton) findViewById(R.id.imageButton2);
        mAuth = FirebaseAuth.getInstance();
        String current_id = mAuth.getCurrentUser().getUid();

        final String[] asd = {""};

        mDatabaseFav = FirebaseDatabase.getInstance().getReference().child("Fav").child(mAuth.getCurrentUser().getUid());
        mDatabaseFav.keepSynced(true);

        mPost_key = getIntent().getExtras().getString("blog_id");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");


        // Retrieving only 1 entry based on key
        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_name = (String) dataSnapshot.child("post_name").getValue();
                String post_descConfirm = (String) dataSnapshot.child("post_desc").getValue();
                String post_image = (String) dataSnapshot.child("post_image").getValue();


                // Toast.makeText(flyer.this, dataSnapshot + "", Toast.LENGTH_SHORT).show();
                tvTitle.setText(post_title);
                tvDesc.setText(post_descConfirm);

                //imageView2.setImage(Context ctx,post_image);

                asd[0] = post_name;
                if (post_name.equalsIgnoreCase("flyer")) {
                    imageView2.setImageResource(R.drawable.sg_flyer);
                } else if (post_name.equalsIgnoreCase("Esplanade")) {
                    imageView2.setImageResource(R.drawable.item1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mDatabaseFav.child(mPost_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        find = dataSnapshot.child("uid").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if (find.equalsIgnoreCase("false") && find != null) {
                    find = "true";
                    Toast.makeText(flyer.this, "Favourited", Toast.LENGTH_SHORT).show();
                } else if (find.equalsIgnoreCase("true")) {
                    find = "false";
                    Toast.makeText(flyer.this, "Removed From Favourites", Toast.LENGTH_SHORT).show();
                } else {
                    find = "true";
                }

                try {

                    Post post = new Post(find);
                    Map<String, Object> postValues = post.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(mPost_key, postValues);
                    mDatabaseFav.updateChildren(childUpdates);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });

        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewIntent = new Intent(flyer.this, ReviewActivity.class);
                reviewIntent.putExtra("post_key", mPost_key);

                startActivity(reviewIntent);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addresss;
                if(asd[0].equalsIgnoreCase("flyer")){
                     addresss = "Singapore Flyer, 30 Raffles Ave, Singapore 039803";
                }else{
                     addresss = "1 Esplanade Dr, Singapore 038981";
                }

                String map = "http://maps.google.com/maps?q=" + addresss;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(intent);
            }
        });
    }

    public void setImage(Context ctx, String image) {
        ImageView post_image = (ImageView) findViewById(R.id.imageViewRow2);
        Picasso.with(ctx).load(image).into(post_image);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch ((item.getItemId())){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);


    }
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }


}




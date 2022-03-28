package com.example.a15017498.touraroundsg_;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class personalFlyer extends AppCompatActivity {


    ImageView imageView;
    TextView txtTitle;
    TextView txtDesc;
    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ImageButton delete;
    String asif = "";
    ImageButton share;


    String post_title;
    String post_desc;
    String post_image;
    String title = "";
    String desc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_flyer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        getSupportActionBar().setTitle("HOTSPOTS");

        Intent intentReceived4 = getIntent();
        final String post_key = intentReceived4.getStringExtra("blog_id");
        mAuth = FirebaseAuth.getInstance();
        imageView = (ImageView) findViewById(R.id.imageView2Personal);
        txtDesc = (TextView) findViewById(R.id.DescDPersonal);
        txtTitle = (TextView) findViewById(R.id.TitleTPersonal);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("personal_blog");
        mPost_key = post_key;
        delete = (ImageButton) findViewById(R.id.imageButtonPersonalDelete);
        share = (ImageButton) findViewById(R.id.whatsapp);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                 mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      title =   dataSnapshot.child("title").getValue().toString().trim();
                        desc = dataSnapshot.child("desc").getValue().toString();
                        Toast.makeText(personalFlyer.this, title+"", Toast.LENGTH_SHORT).show();

                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Look: "+title +"\n"+desc);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(post_key).removeValue();
                Intent mainIntent = new Intent(personalFlyer.this, Main2Activity.class);
                //mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                finish();
            }
        });

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                asif = dataSnapshot.child("user_id").getValue(String.class);
                //Toast.makeText(personalFlyer.this, mAuth.getCurrentUser().getUid() + "", Toast.LENGTH_LONG).show();

                post_title = dataSnapshot.child("title").getValue(String.class);
                post_desc = dataSnapshot.child("desc").getValue(String.class);
                post_image = dataSnapshot.child("image").getValue(String.class);


                if (mAuth.getCurrentUser().getUid().equalsIgnoreCase(asif) == true && asif != null) {
                    txtTitle.setText(post_title);
                    txtDesc.setText(post_desc);
                    Picasso.with(personalFlyer.this).load(post_image).into(imageView);
                } else {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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

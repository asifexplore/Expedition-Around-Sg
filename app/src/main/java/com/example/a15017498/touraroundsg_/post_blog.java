package com.example.a15017498.touraroundsg_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//This is for the developer blog
public class post_blog extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextDesc;
    EditText editTextPostName;
    Button btnSubmit;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    EditText editText4;
    ImageButton imageButton3;
    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;
    private StorageReference mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_blog);

        editTextTitle = (EditText) findViewById(R.id.editText);
        editTextDesc = (EditText) findViewById(R.id.editText2);
        editTextPostName = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        mProgress = new ProgressDialog(this);
        imageButton3 = (ImageButton) findViewById(R.id.imageButtonDeveloper);
        mStorage = FirebaseStorage.getInstance().getReference();


        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        btnSubmit = (Button) findViewById(R.id.button2);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

    }

    private void startPosting() {
        mProgress.setMessage("Storing data into database");
        mProgress.show();

        final String title = editTextTitle.getText().toString().trim();
        final String desc = editTextDesc.getText().toString().trim();
        final String post_name = editTextPostName.getText().toString().trim();
        final String post_desc = editText4.getText().toString().trim();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(post_name) && !TextUtils.isEmpty(post_desc) && mImageUri != null) {

            //DatabaseReference newPost = mDatabase.push();


            StorageReference filepath = mStorage.child("Developer_blog_images").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    // Push creates random id
                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("title").setValue(title);
                    newPost.child("desc").setValue(desc);
                    newPost.child("post_name").setValue(post_name);
                    newPost.child("post_desc").setValue(post_desc);
                    newPost.child("post_image").setValue(downloadUrl.toString());
                    mProgress.dismiss();
                }
            });


            Intent intentMain2Activity = new Intent(post_blog.this, Main2Activity.class);
            startActivity(intentMain2Activity);


        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            //This returns the URI
            mImageUri = data.getData();

            imageButton3.setImageURI(mImageUri);

        }

    }
}

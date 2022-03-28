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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

//This is personal Blog
public class PostActivity extends AppCompatActivity {

    ImageButton imageButtonSelect;
    EditText editTextTitle;
    EditText editTextDesc;
    Button btnSubmit;
    private Uri mImageUri = null;


    private static final int Gallery_REQUEST = 1;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imageButtonSelect = (ImageButton) findViewById(R.id.imageButtonSelect);
        editTextTitle = (EditText) findViewById(R.id.editText4ID);
        editTextDesc = (EditText) findViewById(R.id.editText5Desc);
        btnSubmit = (Button) findViewById(R.id.button3);
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("personal_blog");
        mAuth = FirebaseAuth.getInstance();

        imageButtonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_REQUEST);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

    }

    private void startPosting() {

        mProgress.setMessage("Uploading Post");

        final String title_val = editTextTitle.getText().toString().trim();
        final String desc_val = editTextDesc.getText().toString().trim();

        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && mImageUri != null){
            //Return us name of image
            StorageReference filepath = mStorage.child("personal_blog_image").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadURL = taskSnapshot.getDownloadUrl();

                    //push() creates a random new id, so that it won't ever be overwritten
                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("title").setValue(title_val);
                    newPost.child("desc").setValue(desc_val);
                    newPost.child("image").setValue(downloadURL.toString());
                    newPost.child("user_id").setValue(mAuth.getCurrentUser().getUid());
                    mProgress.dismiss();
                    Intent intent=new Intent(PostActivity.this,Main2Activity.class);
                    startActivity(intent);
                }
            });


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_REQUEST && resultCode == RESULT_OK){
            mImageUri = data.getData();
            imageButtonSelect.setImageURI(mImageUri);
        }

    }


}

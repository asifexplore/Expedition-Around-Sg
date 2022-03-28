package com.example.a15017498.touraroundsg_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetupActivity extends AppCompatActivity {

    EditText NameField;
    Button btnSubmit;

    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        NameField = (EditText) findViewById(R.id.setupNameField);
        btnSubmit = (Button) findViewById(R.id.btnSetUp);
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSetupAccount();
            }
        });

    }

    private void startSetupAccount() {

        String name = NameField.getText().toString().trim();

        String user_id = mAuth.getCurrentUser().getUid();

        //Checking if the Textfield is empty or not
        if(!TextUtils.isEmpty(name)){
            mDatabaseUsers.child(user_id).child("name").setValue(name);
            mProgress.setMessage("Finishing Setup.....");
            mProgress.show();
            mProgress.dismiss();
            Intent SetupIntent = new Intent(SetupActivity.this,Main2Activity.class);
            //PREVENT THE USER FROM GOING BACK
            SetupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(SetupIntent);
        }

    }
}

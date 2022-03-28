package com.example.a15017498.touraroundsg_;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends Activity {

    private EditText name;
    private EditText email;
    private EditText password;
    private Button btn;


    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");



        name = (EditText) findViewById(R.id.editTextName);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        btn = (Button) findViewById(R.id.buttonRegister);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();

            }
        });

    }

    private void startRegister() {
       final  String name1 = name.getText().toString().trim();
        String email1 = email.getText().toString();
        String pass = password.getText().toString();

        if (!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(email1) && !TextUtils.isEmpty(pass)) {
            mProgress.setMessage("Signing Up....");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email1, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference current_user_db = mDatabase.child(user_id);

                        current_user_db.child("name").setValue(name1);
                        current_user_db.child("image").setValue("default");

                        mProgress.dismiss();

                        Intent intent=new Intent(getBaseContext(),LoginActivity.class);
                        startActivity(intent);

                    }
                }
            });
        }

    }
}

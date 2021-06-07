package com.bmat.css_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class createAccount extends AppCompatActivity {

    EditText mName, mEmail,mPassword, mPhoneNum, mCompany;
    Button mSignUpBtn;
    FirebaseAuth fAuth;
    firebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        database = new firebaseDatabase();

        mCompany = findViewById(R.id.signUpCompany);
        mName = findViewById(R.id.signUpName);
        mEmail = findViewById(R.id.signUpEmailAddress);
        mPassword = findViewById(R.id.signUpPassword);
        mPhoneNum = findViewById(R.id.signUpPhoneNum);
        mSignUpBtn = findViewById(R.id.signUpBtn);

        fAuth = FirebaseAuth.getInstance();

//ON BTN SIGN UP ON CLICK
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String name = mName.getText().toString().trim();
                final String company = mCompany.getText().toString().trim();
                final String phone = mPhoneNum.getText().toString().trim();

                if(TextUtils.isEmpty(company)){
                    mCompany.setError("Please enter your company name");
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    mName.setError("Please enter your name");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Empty");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }
                if(password.length() <6){
                    mPassword.setError("Password must be at least 6 characters long");
                    Toast.makeText(createAccount.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                //register firebase
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.createCompany(name, email, company, phone);
                            Toast.makeText(createAccount.this,"User Created",Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(createAccount.this, Login.class);
                            startActivity(mainIntent);
                        }else{
                            Toast.makeText(createAccount.this,"ERROR!" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }
        });

    }
}
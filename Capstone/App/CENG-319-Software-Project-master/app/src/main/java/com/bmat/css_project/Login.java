package com.bmat.css_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //Firebase Stuff
    EditText mEmail, mPassword;
    FirebaseAuth fAuth;
    Button loginBtn;
    TextView forgotTv;
    TextView createaccountTv;
    CheckBox mSaveEmail;
    Button mBtn;


    private static final String SHARED_PREF_NAME = "email";
    private static final String KEY_NAME = "key_email";
    private static final String SHARED_PREF_NAME_PASS = "pass";
    private static final String KEY_NAME_PASS = "key_pass";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.emailAddress);
        mPassword = findViewById(R.id.loginPassword);
        fAuth = FirebaseAuth.getInstance();
        loginBtn = findViewById(R.id.loginBtn);
        mBtn = findViewById(R.id.btnSave);

        displayEmail();

        mBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveEmail();
            }
        });

        //Check if logged in
        if(fAuth.getCurrentUser() !=null){
            View loginBtn = findViewById(R.id.loginBtn);
            View emailField = findViewById(R.id.emailAddress);
            View passwordField = findViewById(R.id.loginPassword);
            View forgot = findViewById(R.id.forgot);
            View create = findViewById(R.id.createaccountTv);
            View emailPhoto = findViewById(R.id.emailloginphoto);
            View passwordPhoto = findViewById(R.id.loginpasswordphoto);
            View save = findViewById(R.id.btnSave);
            View wait = findViewById(R.id.textWait);
            loginBtn.setVisibility(View.GONE);
            emailField.setVisibility(View.GONE);
            passwordField.setVisibility(View.GONE);
            forgot.setVisibility(View.GONE);
            create.setVisibility(View.GONE);
            emailPhoto.setVisibility(View.GONE);
            passwordPhoto.setVisibility(View.GONE);
            save.setVisibility(View.GONE);
            wait.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(Login.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }, 500);
        }

        displayEmail();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

//Check if empty
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Empty");
                    return;
                }
//Check if empty
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainIntent = new Intent(Login.this, MainActivity.class);
                                    startActivity(mainIntent);
                                }
                            }, 500);
                        }else{
                            Toast.makeText(Login.this,"ERROR! " + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        forgotTv = (TextView) findViewById(R.id.forgot);
        forgotTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotIntent = new Intent(Login.this, ForgotPassword.class);
                startActivity(forgotIntent);
            }
        });

        createaccountTv = (TextView) findViewById(R.id.createaccountTv);
        createaccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIntent = new Intent(Login.this, createAccount.class);
                startActivity(createIntent);
            }
        });
    }

    public void displayEmail(){
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sp.getString(KEY_NAME, null);
//        SharedPreferences sp1 = getSharedPreferences(SHARED_PREF_NAME_PASS, MODE_PRIVATE);
//        String pass = sp1.getString(KEY_NAME_PASS, null);

        if(email != null){
            mEmail.setText(email);
//            mPassword.setText(pass);
        }

    }

    public void saveEmail(){
        Toast.makeText(Login.this, "Saved",Toast.LENGTH_SHORT).show();

        String email = mEmail.getText().toString().trim();
//    String pass = mPassword.getText().toString().trim();

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
//        SharedPreferences sp1 = getSharedPreferences(SHARED_PREF_NAME_PASS, MODE_PRIVATE);
//        SharedPreferences.Editor e1 = sp1.edit();

        e.putString(KEY_NAME, email);
        e.apply();

//        e1.putString(KEY_NAME_PASS, pass);
//        e1.apply();

        mEmail.setText("");
//        mPassword.setText("");
        displayEmail();
    }

}
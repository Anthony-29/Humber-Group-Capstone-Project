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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    Button sendCodeBtn;
    EditText mEmail;


    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.forgotEmailAddress);
        sendCodeBtn = findViewById(R.id.sendCodeBtn);

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Please enter an email");
                    return;
                }


                fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPassword.this, "Email Sent", Toast.LENGTH_LONG).show();
                                    Intent sendIntent = new Intent(ForgotPassword.this, Login.class);
                                    startActivity(sendIntent);
                                } else {
                                    Toast.makeText(ForgotPassword.this, "ERROR! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
           }
        });
    }
}
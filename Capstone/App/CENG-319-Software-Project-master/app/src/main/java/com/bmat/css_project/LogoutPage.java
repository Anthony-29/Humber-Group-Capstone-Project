package com.bmat.css_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogoutPage extends AppCompatActivity {

    Button backToLoginbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_page);

        backToLoginbtn = findViewById(R.id.backToLoginbtn);
        backToLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(LogoutPage.this, Login.class);
                startActivity(loginIntent);
                onBackPressed();
            }
        });


    }
}
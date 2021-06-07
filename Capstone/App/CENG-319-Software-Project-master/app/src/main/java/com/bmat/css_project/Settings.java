package com.bmat.css_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {


    TextView logoutBtn;
    TextView settingsUpdate;
    TextView settingsContact;
    TextView settingsAboutUs;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        logoutBtn = (TextView) findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.getInstance().signOut();//logout current user
                Intent logoutIntent = new Intent(Settings.this,Login.class);
                Toast.makeText(Settings.this, "Logout",Toast.LENGTH_SHORT).show();
                startActivity(logoutIntent);
            }
        });


        settingsUpdate = (TextView) findViewById(R.id.settingsUpdate);
        settingsUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UpdateIntent = new Intent(Settings.this, UpdatePage.class);
                Toast.makeText(Settings.this, "Updates",Toast.LENGTH_SHORT).show();
                startActivity(UpdateIntent);
            }
        });

        settingsContact = (TextView) findViewById(R.id.settingsContact);
        settingsContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ContactIntent = new Intent(Settings.this, ContactPage.class);
                Toast.makeText(Settings.this, "Contact",Toast.LENGTH_SHORT).show();
                startActivity(ContactIntent);
            }
        });


        settingsAboutUs = (TextView) findViewById(R.id.settingsAboutUs);
        settingsAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AboutIntent = new Intent(Settings.this, AboutUs.class);
                Toast.makeText(Settings.this, "About Us",Toast.LENGTH_SHORT).show();
                startActivity(AboutIntent);
            }
        });




    }
}
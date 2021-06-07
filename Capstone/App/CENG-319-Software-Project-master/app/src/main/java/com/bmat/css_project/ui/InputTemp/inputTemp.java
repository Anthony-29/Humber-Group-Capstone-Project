package com.bmat.css_project.ui.InputTemp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bmat.css_project.R;
import com.bmat.css_project.firebaseDatabase;
import com.bmat.css_project.ui.Model.Temperature;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class inputTemp extends Fragment {
    Button mUpload;
    EditText mTemp;
    firebaseDatabase database;

    public Temperature temperature;

    public inputTemp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_temp, container, false);

        //Firebase Stuff
        mTemp = view.findViewById(R.id.editTextNumberDecimal);
        mUpload = view.findViewById(R.id.btnUpload);
        temperature = new Temperature();
        database = new firebaseDatabase();

        try {
            mUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    int index = userID.indexOf("-");
                    String email = userID.substring(0, index);
                    String temp = mTemp.getText().toString();
                    if (TextUtils.isEmpty(temp)) {
                        Toast.makeText(getActivity(), "Please enter a temperature", Toast.LENGTH_LONG).show();
                    } else {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();

                        database.createTemp(userID, temp, dtf.format(now));

                        Toast.makeText(getActivity(), "Temperature Inputted " + email, Toast.LENGTH_LONG).show();
                        mTemp.setText("");
                    }
                }
            });
        } catch (Exception e) {

        }

        return view;
    }
}
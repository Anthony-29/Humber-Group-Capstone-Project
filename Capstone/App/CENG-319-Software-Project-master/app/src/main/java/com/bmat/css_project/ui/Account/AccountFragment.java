package com.bmat.css_project.ui.Account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.bmat.css_project.R;
import com.bmat.css_project.firebaseDatabase;
import com.bmat.css_project.ui.Model.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AccountFragment extends Fragment {

    Button mSignUp;
    EditText mName, mEmail, mPassword, mPhone;
    Switch mAdmin;
    private FirebaseAuth mAuth2;
    private FirebaseAuth mAuth1;
    firebaseDatabase database;

    public Employee employee;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth1 = FirebaseAuth.getInstance();

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("cssproject-9fa41-default-rtdb")
                .setApiKey("AIzaSyD0HLrcSehEt_Kz7xxxy--ih2ewf1yePC0")
                .setApplicationId("cssproject-9fa41").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getActivity(), firebaseOptions, "TempUserCreation");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("TempUserCreation"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Firebase Stuff
        View view = inflater.inflate(R.layout.account_fragment, container, false);

        mName = view.findViewById(R.id.registerName);
        mEmail = view.findViewById(R.id.registerEmail);
        mPassword = view.findViewById(R.id.registerPassword);
        mPhone = view.findViewById(R.id.registerPhone);
        mAdmin = view.findViewById(R.id.adminSwitch);
        mSignUp = view.findViewById(R.id.registerBtn);
        employee = new Employee();
        database = new firebaseDatabase();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                Boolean admin = mAdmin.isChecked();

                int end = userID.indexOf("=");
                int index = userID.indexOf("-");
                String company = userID.substring(index + 1, end);

                if(TextUtils.isEmpty(name)){
                    mName.setError("Please enter the employee name");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Please enter the employee name");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Please enter the employee name");
                    return;
                }

                if(password.length() <6){
                    mPassword.setError("Password must be at least 6 characters long");
                    Toast.makeText(getActivity(), "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                //register firebase
                mAuth2.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.createEmployee(name, email, phone, admin, company);
                            Toast.makeText(getActivity(),"Employee Created",Toast.LENGTH_LONG).show();

                            String adminCheck = Boolean.toString(admin);
                            String userID = email + "-" + company + "=" + adminCheck;

                            FirebaseUser user = mAuth2.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userID).build();
                            assert user != null;
                            user.updateProfile(profileUpdates);

                            mAuth2.signOut();
                        }else{
                            Toast.makeText(getActivity(),"ERROR!" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return view;
    }

}
package com.bmat.css_project.ui.Override;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bmat.css_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OverrideFragment extends Fragment {

    private OverrideViewModel overrideViewModel;
    ImageView overridebtn;
    TextView textViewState;

    private DatabaseReference mDatabase;

    String state;
    String tag = "Poo";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        overrideViewModel =
                ViewModelProviders.of(this).get(OverrideViewModel.class);
        View root = inflater.inflate(R.layout.fragment_override, container, false);
//        final TextView textView = root.findViewById(R.id.text_override);
//        overrideViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final Button overridebtn = (Button) root.findViewById(R.id.button);

        textViewState = root.findViewById(R.id.textViewState);

        textViewState.setText("state");

        //FETCHING USER INFO (COMPANY IS NEEDED TO SEE WHAT DEVICE THE OVERRIDE BUTTON CONTROLS)
        String userID = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        int index = userID.indexOf("-");
        int end = userID.indexOf("=");
        String companyName = userID.substring(index + 1, end);

        com.google.firebase.database.Query query = mDatabase.child(companyName).child("sensors").child("lock");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                String state = dataSnapshot.child("State").getValue().toString();

                state = dataSnapshot.child("astate").getValue().toString();

                Log.d(tag, "In the onDataChange query state = " + state);
                if(state.equalsIgnoreCase("open")){
//                    mDatabase.child("Brendon").child("ADMIN").child("State").setValue("Closed");
                    textViewState.setText("disabled");
                    Log.d(tag, "In the state == open");
                }

                if(state.equalsIgnoreCase("closed")){
//                    mDatabase.child("Brendon").child("ADMIN").child("State").setValue("Open");
                    textViewState.setText("enabled");
                    Log.d(tag, "In the state == Closed");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        state = textViewState.getText().toString();

        overridebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                state = textViewState.getText().toString();

                Log.d(tag, "Btn clicked state = " + state);

                if(state == "disabled"){
                    mDatabase.child(companyName).child("sensors").child("lock").child("astate").setValue("closed");
                    Log.d(tag, "In the btn check if open = " + state);
                }

                if(state == "enabled") {
                    mDatabase.child(companyName).child("sensors").child("lock").child("astate").setValue("open");
                    Log.d(tag, "In the btn check else open = " + state);
                }

            }
        });

        return root;
    }
}

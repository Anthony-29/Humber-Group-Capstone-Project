package com.bmat.css_project.ui.PersonalLog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PersonalLogFragment extends Fragment {

    private static final String TAG = "MyApp";
    private PersonalLogViewModel personalLogViewModel;
    private DatabaseReference mDatabase;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapterPersonal;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        personalLogViewModel =
                ViewModelProviders.of(this).get(PersonalLogViewModel.class);
        View view = inflater.inflate(R.layout.fragment_personallog, container, false);

        final ListView list = view.findViewById(R.id.listViewPersonal);
        arrayList = new ArrayList<>();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        int index = userID.indexOf("-");
        int end = userID.indexOf("=");
        String companyName = userID.substring(index + 1, end);
        String email = userID.substring(0, index);

        com.google.firebase.database.Query query = mDatabase.child(companyName).child("temperature").orderByChild("email").equalTo(email);

        arrayList.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                arrayList.clear();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    String date = singleSnapshot.child("date").getValue().toString();
                    String temp = singleSnapshot.child("temp").getValue().toString();

                    String[] newDate = date.split(" ", 2);

                    Log.d(TAG, "In the for loop pls work poop date: " + newDate[0] + "temp: " + temp);

                    arrayList.add("Date: " + newDate[0] + "             Temp: " + temp + "°C");

                }
//                if (getActivity()!=null){
                try {
                    arrayAdapterPersonal = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                    list.setAdapter(arrayAdapterPersonal);

                } catch (Exception e) {

                }

//                }

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        });

        return view;

    }

//    @Override
//    public void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        getActivity().onBackPressed();
//    }

}



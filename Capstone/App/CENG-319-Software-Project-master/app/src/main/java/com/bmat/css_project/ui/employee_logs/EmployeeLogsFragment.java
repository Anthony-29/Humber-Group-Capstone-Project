package com.bmat.css_project.ui.employee_logs;

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

public class EmployeeLogsFragment extends Fragment {

    private EmployeeLogsViewModel employeeLogsViewModel;
    private DatabaseReference mDatabase;
    private static final String TAG = "Employee";
    private ArrayList<String> arrayListEmployee;
    private ArrayAdapter<String> arrayAdapterEmployee;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        employeeLogsViewModel =
                ViewModelProviders.of(this).get(EmployeeLogsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_employee_logs, container, false);
        final ListView list = view.findViewById(R.id.listViewEmployee);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        arrayListEmployee = new ArrayList<>();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        int index = userID.indexOf("-");
        int end = userID.indexOf("=");
        String companyName = userID.substring(index + 1, end);
        String email = userID.substring(0, index);

        com.google.firebase.database.Query query = mDatabase.child(companyName).child("temperature");

        arrayListEmployee.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                    arrayListEmployee.clear();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    String date = singleSnapshot.child("date").getValue().toString();
                    String temp = singleSnapshot.child("temp").getValue().toString();

                    String[] newDate = date.split(" ", 2);

                    Log.d(TAG, "In the for loop pls work poop date: " + newDate[0] + "temp: " + temp);

                    arrayListEmployee.add("Date: " + newDate[0] + "             Temp: " + temp + "°C");

                }

                try {
                    arrayAdapterEmployee = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_employee, arrayListEmployee);
                    list.setAdapter(arrayAdapterEmployee);
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
//        list.setAdapter(arrayAdapter);

        return view;
    }

}

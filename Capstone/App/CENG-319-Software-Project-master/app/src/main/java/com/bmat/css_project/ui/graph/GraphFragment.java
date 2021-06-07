package com.bmat.css_project.ui.graph;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bmat.css_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GraphFragment extends Fragment {

    private static final String TAG = "MyApp";
    private DatabaseReference mDatabase;
    private ArrayList<String> arrayListGraph;
    private GraphView graph;
    private ArrayAdapter<String> arrayAdapterGraph;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        final ListView list = view.findViewById(R.id.graphlistview);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        arrayListGraph = new ArrayList<>();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        int index = userID.indexOf("-");
        int end = userID.indexOf("=");
        String companyName = userID.substring(index + 1, end);
        String email = userID.substring(0, index);

        arrayListGraph.clear();

        com.google.firebase.database.Query query = mDatabase.child(companyName).child("temperature").orderByChild("email").equalTo(email);

        try {

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

//                    arrayListGraph.clear();
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        String date = singleSnapshot.child("date").getValue().toString();
                        String temp = singleSnapshot.child("temp").getValue().toString();

                        String[] newDate = date.split(" ", 2);

//                    Log.d(TAG, "In the for loop pls work poop date: " + newDate[0] + "temp: " + temp);

                        arrayListGraph.add("Date: " + newDate[0] + "             Temp: " + temp + " °C");

                    }
//                    if (getActivity()!=null){
                    try {
                        arrayAdapterGraph = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayListGraph);
                        list.setAdapter(arrayAdapterGraph);
                    } catch (Exception e) {

                    }

                    graph = (GraphView) view.findViewById(R.id.graphView);

                    DataPoint[] datapoints = new DataPoint[arrayListGraph.size()];

                    for (int i = 0; i < arrayListGraph.size(); i++) {
                        String str = arrayListGraph.get(i);
                        String[] newTemp = str.split(" ", 20);

                        int temp = Integer.parseInt(newTemp[15]);
                        datapoints[i] = new DataPoint(i, temp);
                    }

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datapoints);
//                graph.addSeries(series);
                    graph.post(new Runnable() {
                        @Override
                        public void run() {
                            graph.removeAllSeries();
                            graph.addSeries(series);
                        }
                    });

                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {

                }
            });


//        GraphView graph = (GraphView) view.findViewById(R.id.graphView);

//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
//                new DataPoint(1, 97),
//                new DataPoint(2, 98),
//                new DataPoint(3, 97),
//                new DataPoint(4, 105),
//                new DataPoint(5, 103)
//        });
//
//        graph.addSeries(series);

        } catch (Exception e) {
            Log.d(TAG, "Null pointer exception caught");
        }

        return view;

    }

}

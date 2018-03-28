package com.example.bhuiyan.newfirebase12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RetrieveActivity extends AppCompatActivity {
ListView listView;
FirebaseDatabase database;
DatabaseReference ref;
ArrayList<String> list;
ArrayAdapter<String>adapter;
Trip trip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        trip=new Trip();
        listView=(ListView)findViewById(R.id.listView);

        database=FirebaseDatabase.getInstance();
        ref=database.getReference("Fav Trip");
        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.trip_info,R.id.tripInfo,list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot ds:dataSnapshot.getChildren()){
       trip=ds.getValue(Trip.class);
       list.add(trip.getStart().toString()+"\n "+
               trip.getEnd().toString()+"\n "+trip.getComments().toString()+"\n"+trip.getDate().toString()+"\n ");
               }
               listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

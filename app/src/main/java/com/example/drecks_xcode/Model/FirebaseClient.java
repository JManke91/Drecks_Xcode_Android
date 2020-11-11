package com.example.drecks_xcode.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseClient {

    private static final String TAG = "FirebaseClient";

    public static void setNewStatus(Status status) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference statusRef = mDatabase.child("status");
        // Create new UDID database reference
        DatabaseReference uniqueRef = statusRef.push();
        uniqueRef.setValue(status);
    }

    // TODO: Does not work yet -> Build a way to get async data
    public static void getStatusUpdates(final FirebaseResponseInterface firebaseResponseInterface) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("results");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int result = dataSnapshot.getValue(Integer.class);

                Log.d("afterDataSnapshot", "after");
                firebaseResponseInterface.onStatusUpdatesCallback(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("newCancelled", "cancelled");
                firebaseResponseInterface.onErrorCallback();
            }
        };
        ref.addValueEventListener(eventListener);
    }

    public static void requestStatusListUpdates(final FirebaseStatusListResponseInterface firebaseStatusListResponseInterface) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("status");
        Query myQuery = dbRef.orderByChild("date").limitToLast(5);

        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Status> fooList = new ArrayList<>();

                for(DataSnapshot statusSnapshot: dataSnapshot.getChildren()) {
                    Status status = statusSnapshot.getValue(Status.class);
                    // add data to list
                    fooList.add(status);
                }

                // return data
                firebaseStatusListResponseInterface.onStatusListUpdatesCallback(fooList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("canclledEvent", "new event was cancelled");
                firebaseStatusListResponseInterface.onErrorCallback();
            }
        });
    }
}

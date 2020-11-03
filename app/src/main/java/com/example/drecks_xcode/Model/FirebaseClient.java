package com.example.drecks_xcode.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseClient {
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
                firebaseResponseInterface.onCallback(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("newCancelled", "cancelled");
            }
        };
        ref.addValueEventListener(eventListener);
    }
}

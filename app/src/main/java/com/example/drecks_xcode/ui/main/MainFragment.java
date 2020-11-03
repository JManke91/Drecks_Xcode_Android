package com.example.drecks_xcode.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.util.Log;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.drecks_xcode.Model.FirebaseClient;
import com.example.drecks_xcode.Model.FirebaseResponseInterface;
import com.example.drecks_xcode.Model.Status;
import com.example.drecks_xcode.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MainFragment extends Fragment {
    private Button sendRequestButton;
    private TextView resultTextView;
    private DatabaseReference mDatabase;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("onCreatedViewTag", "View was sucessfully created");
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sendRequestButton = getActivity().findViewById(R.id.sendRequestButton);
        resultTextView = getActivity().findViewById(R.id.resultTextView);

        FirebaseClient.getStatusUpdates(new FirebaseResponseInterface() {
            @Override
            public void onCallback(int value) {
                Log.d("callbackTag", "onCallback: ");
                resultTextView.setText(Integer.toString(value));
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("myTag", "clicked");

                // TODO: Move to VM -> API layer by using delegate pattern in VM
                DatabaseReference statusRef = mDatabase.child("someChildNode");
                long dateInMS = new Date().getTime();
                Status currentStatus = new Status("Some Android User", dateInMS, 1);

                FirebaseClient.setNewStatus(currentStatus);
            }
        });
    }
}

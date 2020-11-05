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
import android.widget.ListView;
import android.widget.TextView;

import com.example.drecks_xcode.Model.FirebaseClient;
import com.example.drecks_xcode.Model.FirebaseResponseInterface;
import com.example.drecks_xcode.Model.Status;
import com.example.drecks_xcode.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment {
    // Views
    private Button sendRequestButton;
    private TextView resultTextView;
    private ListView listView;

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

        createViews();
        getStatusUpdates();
        requestStatusListUpdates();
        setupSendRequestButtonAction();
        setupListView();
    }

    private void requestStatusListUpdates() {
        FirebaseClient.requestStatusListUpdates();
    }

    private void setupListView() {
        // Create mocked array list -> replace later with real data
        List<Status> fooList = new ArrayList<>();
        Status firstStatus = new Status("first test", 1000, 1);
        Status secondStatus = new Status("second test", 1000, 1);
        Status thirdStatus = new Status("third very long test", 1000, 1);

        fooList.add(firstStatus);
        fooList.add(secondStatus);
        fooList.add(thirdStatus);

        // Create custom adapter
        ListAdapter customListAdapter = new ListAdapter(getActivity(), R.layout.list_row, fooList);

        // Connect adapter to UI
        listView.setAdapter(customListAdapter);
    }

    private void createViews() {
        sendRequestButton = getActivity().findViewById(R.id.sendRequestButton);
        resultTextView = getActivity().findViewById(R.id.resultTextView);
        listView = (ListView) getActivity().findViewById(R.id.listview_activity_main);
    }

    private void getStatusUpdates() {
        FirebaseClient.getStatusUpdates(new FirebaseResponseInterface() {
            @Override
            public void onCallback(int value) {
                Log.d("callbackTag", "onCallback: ");
                resultTextView.setText(Integer.toString(value));
            }
        });
    }

    private void setupSendRequestButtonAction() {
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

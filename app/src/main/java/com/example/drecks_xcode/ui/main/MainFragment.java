package com.example.drecks_xcode.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.drecks_xcode.Model.FirebaseClient;
import com.example.drecks_xcode.Model.FirebaseResponseInterface;
import com.example.drecks_xcode.Model.FirebaseStatusListResponseInterface;
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
    private ProgressBar progressBar;
    private ProgressBar listProgressBar;

    List<Status> fooList;

    {
        fooList = new ArrayList<>();
    }

    // Create custom adapter
    ListAdapter customListAdapter;

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
        FirebaseClient.requestStatusListUpdates(new FirebaseStatusListResponseInterface() {
            @Override
            public void onStatusListUpdatesCallback(List<Status> value) {
                List<Status> foo = value;
                updateListView(foo);
            }

            @Override
            public void onErrorCallback() {
                listProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setupListView() {
        customListAdapter = new ListAdapter(getActivity(), R.layout.list_row, fooList);

        // Connect adapter to UI
        listView.setAdapter(customListAdapter);
    }

    private void updateListView(List<Status> statusList) {
        // Remove old data from list
        fooList.clear();
        // Add all elements from new list
        for (Status status : statusList) {
            fooList.add(status);
        }

        // Execute UI code on main thread
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                listProgressBar.setVisibility(View.GONE);
                customListAdapter.notifyDataSetChanged();
            }
        };
        mainHandler.post(myRunnable);
    }

    private void createViews() {
        sendRequestButton = getActivity().findViewById(R.id.sendRequestButton);
        resultTextView = getActivity().findViewById(R.id.resultTextView);
        listView = (ListView) getActivity().findViewById(R.id.listview_activity_main);

        listProgressBar = (ProgressBar) getActivity().findViewById(R.id.listProgressBar);
        listProgressBar.setVisibility(View.VISIBLE);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.pBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void getStatusUpdates() {
        FirebaseClient.getStatusUpdates(new FirebaseResponseInterface() {
            @Override
            public void onStatusUpdatesCallback(int value) {
                Log.d("callbackTag", "onCallback: ");
                progressBar.setVisibility(View.GONE);
                resultTextView.setText(Integer.toString(value));
            }

            @Override
            public void onErrorCallback() {
                progressBar.setVisibility(View.GONE);
                resultTextView.setText("Error");
            }
        });
    }

    private void setupSendRequestButtonAction() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long dateInMS = new Date().getTime();
                Status currentStatus = new Status(dateInMS, 1   , "Android User");

                FirebaseClient.setNewStatus(currentStatus);
            }
        });
    }
}

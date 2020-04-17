package com.example.drecks_xcode.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.drecks_xcode.Model.Status;

import java.util.Date;

public class MainViewModel extends ViewModel {
    // Create a LiveData with a String
    private MutableLiveData<String> currentName;
    // TODO: Implement the ViewModel
    String getResults() {
        // TODO: Call FirebaseClient
        return "helloWorld";
    }

    Status createStatus(String name) {

        long dateInMS = new Date().getTime();
        Status currentStatus = new Status(name, dateInMS, 1);
        return currentStatus;
    }

    // Create Live Data Objects
    public MutableLiveData<String> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }
}

package com.example.drecks_xcode.Model;

import android.util.Log;

import java.util.Date;

public class Status {

    private static final String TAG = "Status";

    public long date;
    public Integer strike;
    public String name;

    public Status() {
        // Just for firebase
        Log.d(TAG, "Status: constructor called");
    }

    public Status(long date, Integer strike, String name) {
        this.date = date;
        this.strike = strike;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getDate() {
        return date;
    }

    public Integer getStrike() {
        return strike;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setStrike(Integer strike) {
        this.strike = strike;
    }
}

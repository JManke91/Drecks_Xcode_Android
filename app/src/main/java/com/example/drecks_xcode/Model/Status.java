package com.example.drecks_xcode.Model;

import java.util.Date;

public class Status {
    public String name;
    //public Date date;

    public long dateInMS;
    public Integer strike;

    public Status(String name, long dateInMS, Integer strike) {
        this.name = name;
        this.dateInMS = dateInMS;
        this.strike = strike;
    }
}

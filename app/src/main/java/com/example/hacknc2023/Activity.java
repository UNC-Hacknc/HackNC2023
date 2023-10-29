package com.example.hacknc2023;

import java.util.ArrayList;
import java.util.Date;

public class Activity {

    public int id;
    public ArrayList<Integer> groupIds;
    public String interest;
    public Date time;
    public String location;

    public Activity() {
        int id = 000000000;
        groupIds = new ArrayList<>();
        interest = "indian cuisine";
        Date date = new Date();
        time = new Date();
        location = "00111";
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Activity(ArrayList<Integer> groupIds, String interest, Date time, String location){
        this.id = id;
        this.groupIds = groupIds;
        this.interest = interest;
        this.time = time;
        this.location = location;
    }

}

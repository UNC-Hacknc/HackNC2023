package com.example.hacknc2023;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HandleDatabase {

    private DatabaseReference mDatabase;

    public HandleDatabase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public ArrayList<Activity> getActivities(int id){
        ArrayList<Activity> activities = new ArrayList<>();
        mDatabase.child("users").child(id+"").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    activities.add((Activity) task.getResult().getValue());
                }
            }
        });
        return activities;
    }
}

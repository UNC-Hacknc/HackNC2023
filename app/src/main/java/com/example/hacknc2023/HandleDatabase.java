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

    private static DatabaseReference mDatabase = null;

    private static HandleDatabase databaseClass = null;

    // Static method
    // Static method to create instance of Singleton class
    public static synchronized HandleDatabase getInstance()
    {
        if (databaseClass == null)
            databaseClass = new HandleDatabase();

        return databaseClass;
    }
    private HandleDatabase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public ArrayList<Activity> getActivities(int id){
        ArrayList<Activity> activities = new ArrayList<>();
        if(mDatabase == null){ //Error checking to make sure database has something
            return null;
        }
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

    public void findOrCreateActivities(int currUserId, String interest) {

    }
}

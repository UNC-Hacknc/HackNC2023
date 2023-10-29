package com.example.hacknc2023;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class HandleDatabase {

    private static DatabaseReference mDatabase = null;
    private static HandleDatabase databaseClass = null;
    private boolean usersExist = false;
    private boolean activitiesExist = false;

    int maxGroupSize = 4;

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
        mDatabase.child("activities").child(id+"").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
        mDatabase.child("activities").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Exist! Do whatever.
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        // TODO: handle the post
                        Log.i("ReturnedObject", String.valueOf(postSnapshot.getValue()));
                        Activity activity = postSnapshot.getValue(Activity.class);
                        if(activity.interest.equals(interest) && activity.groupIds.size() < maxGroupSize){
                            Log.i("FoundPossibleAct", "found");
                            updateActivity(currUserId, activity, postSnapshot.getKey());
                            return;
                        }
                    }
                    createNewActivity(currUserId, interest);
                } else {
                    // Don't exist! Create First Activity as well as the database.
                    createNewActivity(currUserId, interest);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed, how to handle?
                //TODO Add to this if onCancelled ever happens and is an issue
            }

        });
    }

    private void createNewActivity(int currUserId, String interest) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(currUserId);
        mDatabase.child("activities").push().setValue(new Activity(arrayList, interest, new Date(), "somewhere"));
    }

    private void updateActivity(int currUserId, Activity activity, String key) {
        activity.groupIds.add(currUserId);
        mDatabase.child("activities").child(key).setValue(activity);
    }

    public int createNewUser() {
        final int[] userId = {000000000};

        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Exist! Do whatever.
                    snapshot.getChildrenCount();
                    userId[0] += snapshot.getChildrenCount();
                    mDatabase.child("users").child(userId[0] + "").setValue(new User(
                            userId[0], MainActivity.name, MainActivity.interests
                    ));
                    MainActivity.currUserId = userId[0];
                    Log.i("firebaseUser", HandleDatabase.getInstance().getUser(userId[0]));
                } else {
                    // Don't exist! Create First User as well as the database.
                    mDatabase.child("users").child("000000000").setValue(new User(
                            000000000, MainActivity.name, MainActivity.interests
                    ));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed, how to handle?
                //TODO Add to this if onCancelled ever happens and is an issue
            }

        });
        return userId[0];
    }

    public String getUser(int currUserId) {
        final String[] returnVal = {""};
        mDatabase.child("users").child(currUserId+"").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.i("TypeClass", String.valueOf(task.getResult().getValue()));
                }
            }
        });
        return returnVal[0];
    }
}

package com.example.hacknc2023;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class HandleDatabase {

    private static DatabaseReference mDatabase = null;
    private static HandleDatabase databaseClass = null;
    private boolean usersExist = false;
    private boolean activitiesExist = false;
    private String meetingLocation;



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

    public void findOrCreateActivities(int currUserId, String interest, MainActivity appCompatActivity) {
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
                            updateActivity(currUserId, activity, postSnapshot.getKey(), appCompatActivity);
                            return;
                        }
                    }
                    createNewActivity(currUserId, interest, appCompatActivity);
                } else {
                    // Don't exist! Create First Activity as well as the database.
                    createNewActivity(currUserId, interest, appCompatActivity);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed, how to handle?
                //TODO Add to this if onCancelled ever happens and is an issue
            }
        });
    }

    private void createNewActivity(int currUserId, String interest, MainActivity activity) {
        GoogleApiPlaces googleApiPlaces = new GoogleApiPlaces();
        String location = googleApiPlaces.searchLocation(interest);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(currUserId);
        activity.updateActivity(location, interest);


        mDatabase.child("activities").push().setValue(new Activity(arrayList, interest, new Date(), location));
    }

    private void updateActivity(int currUserId, Activity activity, String key, MainActivity mainActivity) {
        activity.groupIds.add(currUserId);
        mDatabase.child("activities").child(key).setValue(activity);
        String interest = activity.interest;
        String location = activity.location;
        mainActivity.updateActivity(location, interest);
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

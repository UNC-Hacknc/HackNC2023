package com.example.hacknc2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static int currUserId;
    public static String name;
    public static String interests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNewUser();
        Log.i("firebaseUser", HandleDatabase.getInstance().getUser(currUserId));
    }

    public void updateActivity(String location, String activity){
        LinearLayout activityLayout = findViewById(R.id.newActivityLayout);
        TextView locationText = findViewById(R.id.locationText);
        TextView activityText = findViewById(R.id.activityText);
        locationText.setText(location);
        activityText.setText(activity);
        activityLayout.setVisibility(View.VISIBLE);
    }
    private void createNewUser() {
        HandleDatabase handDb = HandleDatabase.getInstance();
        name = "John Doe";
        interests = "food, sports";
        currUserId = handDb.createNewUser();
    }

    public void beginForm(View view) {
        AddActivity dialogFragment = new AddActivity(this);
        dialogFragment.show(getSupportFragmentManager(), "UserFormDialog");
    }
}
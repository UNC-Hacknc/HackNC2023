package com.example.hacknc2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
  
    private void createNewUser() {
        HandleDatabase handDb = HandleDatabase.getInstance();
        currUserId = handDb.createNewUser();
    }

    public void beginForm(View view) {
        AddActivity dialogFragment = new AddActivity();
        dialogFragment.show(getSupportFragmentManager(), "UserFormDialog");
    }
}
package com.example.hacknc2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void beginForm(View view) {
        AddActivity dialogFragment = new AddActivity();
        dialogFragment.show(getSupportFragmentManager(), "UserFormDialog");
    }
}
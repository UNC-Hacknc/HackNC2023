package com.example.hacknc2023;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int currUserId;
    public static String name;
    public static String interests;
    private PlacesClient placesClient;
    private String meetingLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNewUser();
        Log.i("firebaseUser", HandleDatabase.getInstance().getUser(currUserId));
        Places.initialize(getApplicationContext(), "AIzaSyAjpMcfOWq14zsy-2CxTEnLWhxOPCvbq2M");
        placesClient = Places.createClient(this);

    }


    private String searchLocation(String keyword) {

        Thread thread = new Thread(new Runnable() {

            public void run() {
                OkHttpClient client = new OkHttpClient();
                String apiKey = "AIzaSyAjpMcfOWq14zsy-2CxTEnLWhxOPCvbq2M";
                String location = "35.905463, -79.048869";
                int radius = 5000;
                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + keyword + "&location=" + location + "&radius=" + radius + "&key=" + apiKey;

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray results = jsonObject.getJSONArray("results");
                    Random random = new Random();
                    int randomIndex = random.nextInt(results.length());
                    JSONObject place = results.getJSONObject(randomIndex);
                    meetingLocation = place.getString("name");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return meetingLocation;
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
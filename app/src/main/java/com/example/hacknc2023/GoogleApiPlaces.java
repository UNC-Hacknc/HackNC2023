package com.example.hacknc2023;

import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleApiPlaces {

    private String meetingLocation;

    public String searchLocation(String keyword) {

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
}

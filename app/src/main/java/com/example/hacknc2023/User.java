package com.example.hacknc2023;

public class User {

    public int id;
    public String username;
    public String interests;

    public User() {
        this.username = "John Doe";
        this.id = 111111111;
        this.interests = "volleyball, indian cuisine";
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(int id, String username, String interests) {
        this.id = id;
        this.username = username;
        this.interests = interests;
    }
}

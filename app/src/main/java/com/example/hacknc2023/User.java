package com.example.hacknc2023;

public class User {

    public int id;
    public String username;
    public String email;

    public User() {
        this.username = "John Doe";
        this.id = 111111111;
        this.email = "ghejrkn@gmail.com";
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, int id) {
        this.username = username;
        this.email = email;
        this.id = id;
    }
}

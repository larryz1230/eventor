package com.example.eventor;

import java.util.ArrayList;

public class User {
    private int id;
    private String fname, lname, email;
    private ArrayList <User> friends;

    public User (int id, String fname, String lname, String email){
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        friends = new ArrayList<>();
    }

    public String getLname() {
        return lname;
    }

    public String getFname() {
        return fname;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void addFriend (User u){
        friends.add(u);
    }
}

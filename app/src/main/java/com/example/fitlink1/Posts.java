package com.example.fitlink1;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Posts {
    private String email;
    private String workout;
    private String comments;

    public Posts(String email, String workout, String comments) {
        this.email = email;
        this.workout = workout;
        this.comments = comments;
    }

    public Posts(String email, String workout){
        this.email = email;
        this.workout = workout;
        this.comments = "";
    }

    public Posts() {
    }

    public String getEmail() {
        return email;
    }

    public String getWorkout() {
        return workout;
    }

    public String getComments() {
        return comments;
    }


}

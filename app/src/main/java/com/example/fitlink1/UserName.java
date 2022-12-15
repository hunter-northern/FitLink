package com.example.fitlink1;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * THis class is used to create the model for usernames
 * It is used in the Search class and SearchAdapterclass
 */


@IgnoreExtraProperties
public class UserName {
    String username;

    public UserName(){

    }

    public UserName(String username) {

        this.username = username;
    }


    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }
}

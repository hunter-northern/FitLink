package com.example.fitlink1;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Post class to take each post object and adapt them into our arraylist of posts
 * each holds an email, workout, and comment string
 */
@IgnoreExtraProperties
public class Posts {
    private String email;
    private String workout;
    private String comments;

    /**
     * Constructor
     * @param email email of user
     * @param workout workout string post
     * @param comments the given comments
     */
    public Posts(String email, String workout, String comments) {
        this.email = email;
        this.workout = workout;
        this.comments = comments;
    }

    /**
     * another shorter constructor with defualt no comments
     * @param email email of user
     * @param workout workout string user is posting
     */
    public Posts(String email, String workout){
        this.email = email;
        this.workout = workout;
        this.comments = "";
    }

    /**
     * empty constructor
     */
    public Posts() {
    }

    /**
     *
     * @return user email of post
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return workout string of post
     */
    public String getWorkout() {
        return workout;
    }

    /**
     *
     * @return the string of comments
     */
    public String getComments() {
        return comments;
    }


}

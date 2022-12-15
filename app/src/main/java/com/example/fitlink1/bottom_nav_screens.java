package com.example.fitlink1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * bottom nav screens is our main activity for controlling our fragments and showing the different screens
 */
public class bottom_nav_screens extends AppCompatActivity {

    /**
     * These are our main variables we used to instantiate the views and classes
     * Bottom Nav View is a representative of this class
     * main feed frag is the fragment for our main feed activity
     * searchFrag is the fragment for our search activity
     * profileFrag is the fragment for our profile activity
     * usrEmail is passed from our login activity as an intent extra to hold our users id
     */
    BottomNavigationView bottomNavigationView;
    MainFeed mainFeedFrag = new MainFeed();
    Search searchFrag = new Search();
    Profile profFrag = new Profile();
    String usrEmail = "";
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav_screens);
        Intent i = getIntent();
        usrEmail = i.getExtras().getString("username");
        Bundle bundle = new Bundle();
        bundle.putString("username", usrEmail);
        mainFeedFrag.setArguments(bundle);
        searchFrag.setArguments(bundle);
        profFrag.setArguments(bundle);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFeedFrag).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_main_feed:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFeedFrag).commit();
                        return true;
                    case R.id.navigation_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFrag).commit();
                        return true;
                    case R.id.navigation_user_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profFrag).commit();
                        return true;
                }

                return false;
            }
        });
    }
}
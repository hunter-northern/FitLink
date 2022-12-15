package com.example.fitlink1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class bottom_nav_screens extends AppCompatActivity {

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
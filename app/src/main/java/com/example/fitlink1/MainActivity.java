package com.example.fitlink1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword("firstuser@gma.com", "test123");
        Button signup = (Button) findViewById(R.id.signup);
        EditText email = (EditText) findViewById(R.id.textEmailAddress);
        EditText password = (EditText) findViewById(R.id.textPassword);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser currUser = mAuth.getCurrentUser();
        if(currUser != null){
            currUser.reload();
        }
    }



}
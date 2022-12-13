package com.example.fitlink1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static FirebaseAuth mAuth;
    private EditText email, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //FirebaseAuth.getInstance().createUserWithEmailAndPassword("firstuser@gma.com", "test123");
        Button signup = findViewById(R.id.signup);
         email = findViewById(R.id.textEmailAddress);
         password = findViewById(R.id.textPassword);


         //signup.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString());
            //}
        //});


        //Commented out to not continuously update the database each time app is opened
        //
        //
        //Map<String, Object> users = new HashMap<>();
        //users.put("username", "JohnDoe");
        //users.put("email", "jd@gmail.com");
        //db.collection("users").add(users);
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser currUser = mAuth.getCurrentUser();
        if(currUser != null){
            currUser.reload();
        }
    }

    public void onSignInClicked( View v){
        String txt_email = email.getText().toString();
        String txt_password = password.getText().toString();
        signIn(txt_email, txt_password);
    }

    public void onSignUpClicked (View view){
        String txt_email = email.getText().toString();
        String txt_password = password.getText().toString();
        createAccount(txt_email, txt_password);
    }

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, bottom_nav_screens.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                           // Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                           //         Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }


    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        Intent myIntent = new Intent(MainActivity.this, bottom_nav_screens.class);
        myIntent.putExtra("username", email);
        MainActivity.this.startActivity(myIntent);

//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(MainActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(MainActivity.this, bottom_nav_screens.class));
//                            finish();
//                            //updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    //Toast.LENGTH_SHORT).show();
//                            //updateUI(null);
//                        }
//                    }
//                });
        // [END sign_in_with_email]
    }



}
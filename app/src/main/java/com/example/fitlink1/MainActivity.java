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

/**
 * This class is responsible for the authentication and account creation
 */

public class MainActivity extends AppCompatActivity {
    private static FirebaseAuth mAuth;
    private EditText email, password;
    FirebaseFirestore db;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //FirebaseAuth.getInstance().createUserWithEmailAndPassword("firstuser@gma.com", "test123");
        Button signup = findViewById(R.id.signup);
         email = findViewById(R.id.textEmailAddress);
         password = findViewById(R.id.textPassword);
        String docEmail;


    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser currUser = mAuth.getCurrentUser();
        if(currUser != null){
            currUser.reload();
        }
    }

    /**
     * Receives the email and password data entered into the edit text fields
     * @param v
     */

    public void onSignInClicked( View v){
        String txt_email = email.getText().toString();
        String txt_password = password.getText().toString();
        signIn(txt_email, txt_password);
    }

    /**
     * Receives the email and password data entered into the edit text fields
     * @param v
     */

    public void onSignUpClicked (View view){
        String txt_email = email.getText().toString();
        String txt_password = password.getText().toString();
        createAccount(txt_email, txt_password);
    }

    /**
     * Creates an accound via firebase. On success calls upon createuserdoc
     * and starts the mainfeed activity
     * @param email user input email
     * @param password user input password
     */

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
                            String id = user.getUid();
                            createUserDoc(email, id);
                            Toast.makeText(MainActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(MainActivity.this, bottom_nav_screens.class);
                            myIntent.putExtra("username", email);
                            startActivity(myIntent);
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

    /**
     * Authenticates users accound information via firebase
     * onsuccess Mainfeed activity is started
     * @param email user input email
     * @param password user input password
     */

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        //Intent myIntent = new Intent(MainActivity.this, bottom_nav_screens.class);
        //MainActivity.this.startActivity(myIntent);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MainActivity.this, bottom_nav_screens.class));
                            Intent myIntent = new Intent(MainActivity.this, bottom_nav_screens.class);
                            myIntent.putExtra("username", email);
                            MainActivity.this.startActivity(myIntent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    //Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

        // [END sign_in_with_email]
    }

    /**
     * Creates a user document containing the users username and email.
     * document is created using same user Id from firebase Authentication
     * @param email user input email
     * @param id id generated from user account
     */

public void createUserDoc(String email, String id){

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    String emailShort = email;

    String[] For_split_email=emailShort.split("[@._]");
    username = For_split_email[0];

    Toast.makeText(MainActivity.this, username + " CREATED!", Toast.LENGTH_SHORT).show();

    Map<String, Object> city = new HashMap<>();
    city.put("username", username);
    city.put("email", email);

    db.collection("users").document(id).set(city);
}

}
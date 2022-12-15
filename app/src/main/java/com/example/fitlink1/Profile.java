package com.example.fitlink1;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /**
     * our universal variables for the porfile class
     * fsPosts is our reference to our database
     * user is the TextView of the users username
     * postList is an array of posts that is filled by a firestore request
     * pAdpater adapts the postList into our view
     * postListView is our post list view
     * username is the passed in email of the user
     * workout is the string taken from the edit text to post for the user
     * workEdit is the EditText to type the workout into
     * submit is the button to post the workout to firestore
     */
    private static final String ARG_PARAM1 = "username";
    FirebaseFirestore fsPosts;
    public TextView user;
    ArrayList<Posts> postList = new ArrayList<>();
    PostAdapter pAdapter;
    ListView postListView;
    // TODO: Rename and change types of parameters
    private String username;
    public String workout;
    public EditText workEdit;
    public Button submit;


    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * the initial on create for this fragment
     * @param savedInstanceState previous state info
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
        }

    }

    /**
     * onCreateView that handles and is used each time the fragment is tapped on
     * @param inflater inflates the fragment into the view of the layout
     * @param container container holds the view in its group
     * @param savedInstanceState previous state into
     * @return View to set into the layout manager
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /**
         * root view of the inflater in order to access layout components
         * user is the text view set to the username of the user
         * reinstantiate the postList array in order to avoid copies
         * on click listener for the submit button to submit posts
         * onSuccess listener and onFaliure listener to tell how the firestore request faired and then responding properly
         */
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);
        user = (TextView) root.findViewById(R.id.username);
        user.setText(username);
        workEdit = (EditText) root.findViewById(R.id.workout);
        submit = (Button) root.findViewById(R.id.submit);
        fsPosts = FirebaseFirestore.getInstance();
        postListView = root.findViewById(R.id.list_view_profile);
        postList = new ArrayList<>();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });

        fsPosts.collection("posts").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                Toast.makeText(getContext(), "Req Successful", Toast.LENGTH_SHORT).show();
                if (!queryDocumentSnapshots.isEmpty()) {
                    Toast.makeText(getContext(), "Check 2", Toast.LENGTH_SHORT).show();
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot document : list) {
                        Posts p = document.toObject(Posts.class);
                        if(Objects.isNull(p)){
                            Toast.makeText(getContext(), "Null Object", Toast.LENGTH_SHORT).show();
                        } else {
                            if(username.equals(p.getEmail())){
                                postList.add(p);
                            }
                        }

                    }
                    pAdapter = new PostAdapter(getContext(), postList);
                    postListView.setAdapter(pAdapter);
                } else {
                    Toast.makeText(getContext(), "List Empty", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(getContext(), "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    public void InsertData(){
        String usrEmail = username;
        String workout = workEdit.getText().toString();
        Posts post = new Posts(usrEmail, workout);
        fsPosts.collection("posts").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Post Successful", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Post Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
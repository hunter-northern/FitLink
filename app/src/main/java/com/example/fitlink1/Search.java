package com.example.fitlink1;

import android.app.appsearch.SearchResult;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitlink1.databinding.FragmentMainFeedBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *frag that populates the search adapter with username information from firestore
 */

public class Search extends Fragment {

    RecyclerView recyclerView;
    ArrayList<UserName> userArrayList = new ArrayList<>();
    SearchAdapter myAdapter;
    FirebaseFirestore db;
    ListView userListView;
    private static final String username = "username";
    private String mParam1;
    TextView searchInput;
    String searchResult = "";
    Button button;
    SearchView searchView;
    Context context;
    Button searchButton;


    public Search() {
        // Required empty public constructor
    }


    public static Search newInstance(String param1) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putString(username, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(username);
        }


    }

    /**
     * Oncreateview inflates the views within the fragment and uses to UserName and SearchAdapter
     * classes to pupulate and arraylist and fill the listviews with that data
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the view inflated within this fragment
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        userArrayList = new ArrayList<>();

        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        db = FirebaseFirestore.getInstance();

        userListView = root.findViewById(R.id.searchRecycler);
        searchButton = root.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = root.findViewById(R.id.searchInput);
                searchResult = searchInput.getText().toString();
                userArrayList = new ArrayList<>();

                db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        Toast.makeText(getContext(), "Req Successful", Toast.LENGTH_SHORT).show();
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(getContext(), "Check 2", Toast.LENGTH_SHORT).show();
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot document : list) {
                                UserName p = document.toObject(UserName.class);
                                if(Objects.isNull(p)){
                                    Toast.makeText(getContext(), "Null Object", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!(p.getUsername().indexOf(searchResult) == -1)) {
                                        userArrayList.add(p);
                                    }
                                    if(searchResult == null){
                                        Toast.makeText(getContext(), "searched", Toast.LENGTH_SHORT).show();
                                        //UserName s = userArrayList.iterator();
                                        myAdapter.getFilter().filter(searchResult);
                                    }

                                }

                            }
                            myAdapter = new SearchAdapter(getContext(), userArrayList);
                            userListView.setAdapter(myAdapter);
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

            }
        });







        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                Toast.makeText(getContext(), "Req Successful", Toast.LENGTH_SHORT).show();
                if (!queryDocumentSnapshots.isEmpty()) {
                    Toast.makeText(getContext(), "Check 2", Toast.LENGTH_SHORT).show();
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot document : list) {
                        UserName p = document.toObject(UserName.class);
                        if(Objects.isNull(p)){
                            Toast.makeText(getContext(), "Null Object", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!(p.getUsername().indexOf(searchResult) == -1)) {
                                userArrayList.add(p);
                            }
                            if(searchResult == null){
                                Toast.makeText(getContext(), "searched", Toast.LENGTH_SHORT).show();
                                //UserName s = userArrayList.iterator();
                                myAdapter.getFilter().filter(searchResult);
                            }

                        }

                    }
                    myAdapter = new SearchAdapter(getContext(), userArrayList);
                    userListView.setAdapter(myAdapter);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


}
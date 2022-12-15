package com.example.fitlink1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFeed extends Fragment {
    RecyclerView recycler;
    ArrayList<Posts> postList = new ArrayList<>();
    PostAdapter pAdapter;
    FirebaseFirestore fsPosts;
    ListView postListView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String username = "username";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public MainFeed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MainFeed.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFeed newInstance(String param1) {
        MainFeed fragment = new MainFeed();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View root = inflater.inflate(R.layout.fragment_main_feed, container, false);
        //recycler = root.findViewById(R.id.recycleview);
        fsPosts = FirebaseFirestore.getInstance();
        postList = new ArrayList<>();
//        recycler.setAdapter(pAdapter);
//        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        postListView = root.findViewById(R.id.listView);


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
                            if(!mParam1.equals(p.getEmail())){
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
}
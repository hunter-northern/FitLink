package com.example.fitlink1;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
//
//
//adapter class for usernames and following users


public class SearchAdapter extends ArrayAdapter<UserName> implements Filterable{
    FirebaseFirestore db;
    Context context;
    ArrayList<UserName> userArrayList;
    String searched = null;
    List<UserName> userList;
    List<UserName> mStringFilterList;

    public SearchAdapter(Context context, ArrayList<UserName> userArrayList) {
        super(context, 0, userArrayList);
    }

    public View getView(int position, @Nullable View convView, @NonNull ViewGroup parent){
        View listItems = convView;
        if (listItems == null) {
            listItems = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        UserName p = getItem(position);
        TextView email = listItems.findViewById(R.id.userNameView);
        email.setText(p.getUsername());


        listItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
                String id = user.getUid();
                String Added = p.getUsername(); // trying to get array from firestore to update friendlist
                DocumentReference Ref = db.collection("users").document(id);
                Ref.update("friendsList", FieldValue.arrayUnion(Added));

                Toast.makeText(getContext(), "User Followed: " + p.getUsername(),Toast.LENGTH_SHORT).show();
            }
        });

        return listItems;
    }

}

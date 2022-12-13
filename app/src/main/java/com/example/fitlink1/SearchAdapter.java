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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SearchAdapter extends ArrayAdapter<UserName> {
    FirebaseFirestore db;
    Context context;
    ArrayList<UserName> userArrayList;

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
                // on the item click on our list view.
                // we are displaying a toast message.
                Toast.makeText(getContext(), "User Followed",Toast.LENGTH_SHORT).show();

            }
        });

        return listItems;
    }


    //@NonNull
    //@Override
    //public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
//
//        return new ViewHolder(v);
//    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        UserName username = userArrayList.get(position);

//        holder.username.setText(username.username);

//    }

//    @Override
//    public int getItemCount() {
//        return userArrayList.size();
//    }
//
 //   static class ViewHolder extends RecyclerView.ViewHolder {

//        TextView username;

//        public ViewHolder(View itemView) {
//            super(itemView);

//            username = itemView.findViewById(R.id.email);

//        }
//    }
}

package com.example.fitlink1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Posts> {

    Context context;
    ArrayList<Posts> list;

    public PostAdapter(Context context, ArrayList<Posts> list) {
        super(context, 0, list);
    }

    public View getView(int position, @Nullable View convView, @NonNull ViewGroup parent){
        View listItems = convView;
        if (listItems == null) {
            listItems = LayoutInflater.from(getContext()).inflate(R.layout.postentry, parent, false);
        }
        Posts p = getItem(position);
        TextView email = listItems.findViewById(R.id.textEmailAddress);
        TextView workout = listItems.findViewById(R.id.textWorkout);
        TextView comments = listItems.findViewById(R.id.Comments);
        email.setText(p.getEmail());
        workout.setText(p.getWorkout());
        //comments.setText(p.getComments()[0]);
        return listItems;
    }

//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.postentry, parent, false);
//        return new MyViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Posts post = list.get(position);
//        holder.email.setText(post.getEmail());
//        holder.workout.setText(post.getWorkout());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//        TextView email, workout, comments;
//        EditText newComment;
//        Button submit;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            email = itemView.findViewById(R.id.textEmailAddress);
//            workout = itemView.findViewById(R.id.textWorkout);
//            comments = itemView.findViewById(R.id.Comments);
//            newComment = itemView.findViewById(R.id.eTextComment);
//            submit = itemView.findViewById(R.id.commentSub);
//        }
//    }

}

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

/**
 * Class to adapt a an array of posts to fill a list view
 */
public class PostAdapter extends ArrayAdapter<Posts> {
    /**
     * the given context
     * the array list of posts
     */
    Context context;
    ArrayList<Posts> list;

    /**
     * General constructor for Post Adapter
     * @param context context
     * @param list list of posts to adapt
     */
    public PostAdapter(Context context, ArrayList<Posts> list) {
        super(context, 0, list);
    }

    /**
     * gets and returns the list view
     * @param position position within the array of posts
     * @param convView convert view is the passed in view to adapts
     * @param parent view group listeview is apart of
     * @return listview items in fragments
     */
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

}

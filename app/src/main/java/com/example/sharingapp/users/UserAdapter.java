package com.example.sharingapp.users;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sharingapp.R;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;

    public UserAdapter(Context context, ArrayList<User> users,  int textViewResourceId) {
        super(context, textViewResourceId, users);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        User user = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = inflater.from(getContext()).inflate(R.layout.list_user, parent, false);
            TextView username_tv = (TextView) convertView.findViewById(R.id.username_tv);
            TextView email_tv = (TextView) convertView.findViewById(R.id.email_tv);
            username_tv.setText(user.getUsername());
            email_tv.setText(user.getEmail());
        }

        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent edit = new Intent(getContext(), EditUserActivity.class);
                edit.putExtra("position", position);
                getContext().startActivity(edit);
            }
        });

        return convertView;
    }
}

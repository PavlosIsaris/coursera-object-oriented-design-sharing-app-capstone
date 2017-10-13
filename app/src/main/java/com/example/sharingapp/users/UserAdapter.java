package com.example.sharingapp.users;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.sharingapp.R;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;
    private Fragment fragment;

    public UserAdapter(Context context, ArrayList<User> users, Fragment fragment) {
        super(context, 0, users);
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = inflater.from(getContext()).inflate(R.layout.list_user, parent, false);
        }

        return convertView;
    }
}

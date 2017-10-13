package com.example.sharingapp.users;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sharingapp.ItemList;

public class ContactsActivity extends AppCompatActivity {

    private UserList userList;
    private UserList activeBorrowersList;
    private ItemList itemList;
    private ListView myContacts;
    private ArrayAdapter<User> adapter;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void addUserActivity(View view) {
        System.out.println("add users");
    }
}

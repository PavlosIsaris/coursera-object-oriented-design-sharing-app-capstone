package com.example.sharingapp.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.sharingapp.ItemList;
import com.example.sharingapp.R;

public class ContactsActivity extends AppCompatActivity {

    private UserList userList;
    private UserList activeBorrowersList;
    private ItemList itemList;
    private ListView myContacts;
    private UserAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userList = new UserList();
        System.out.println("ON create");
        setContentView(R.layout.activity_contacts);
        myContacts = (ListView) findViewById(R.id.contacts);
        userList.loadUsers(this);
        adapter = new UserAdapter(this, userList.getUsers(), R.layout.list_user);

        myContacts.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        userList.loadUsers(this);
        adapter = new UserAdapter(this, userList.getUsers(), R.layout.list_user);

        myContacts.setAdapter(adapter);
    }

    public void addUserActivity(View view) {
        System.out.println("add users");
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }
}

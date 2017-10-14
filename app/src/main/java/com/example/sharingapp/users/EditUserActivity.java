package com.example.sharingapp.users;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sharingapp.R;
import com.example.sharingapp.users.UserList;

public class EditUserActivity extends AppCompatActivity {

    private UserList userList = new UserList();
    private User user;
    private Context context;

    private EditText username;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        username = (EditText) findViewById(R.id.username_et);
        email = (EditText) findViewById(R.id.email_et);


        context = getApplicationContext();
        userList.loadUsers(context);

        // get intent from HomeActivity
        Intent intent = getIntent();
        int pos = intent.getIntExtra("position", 0);

        user = userList.getUser(pos);

        username.setText(user.getUsername());
        email.setText(user.getEmail());
    }


    public void deleteUser(View view) {
        userList.removeUser(user);
        userList.saveUsers(context);

         /* End EditItemActivity */
        finish();
    }

    public void saveUser(View view) {

        String username_str = username.getText().toString();
        String email_str = email.getText().toString();


        if (username_str.equals("")) {
            username.setError("Empty field!");
            return;
        }

        if (email_str.equals("")) {
            email.setError("Empty field!");
            return;
        }


        // Reuse the item id
        String id = user.getId();
        userList.removeUser(user);

        User updated_user = new User(id, email_str, username_str);

        userList.addUser(updated_user);

        userList.saveUsers(context);

        /* End EditItemActivity */
        finish();
    }

}

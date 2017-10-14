package com.example.sharingapp.users;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sharingapp.R;
import com.example.sharingapp.users.UserList;

import java.util.Random;

/**
 * Add a new item
 */
public class AddUserActivity extends AppCompatActivity {

    private EditText username;
    private EditText email;

    private UserList userList = new UserList();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        username = (EditText) findViewById(R.id.username_et);
        email = (EditText) findViewById(R.id.email_et);
        context = getApplicationContext();
        userList.loadUsers(context);
    }

    public void saveUser (View view) {

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

        User user = new User(randString(5), email_str, username_str);

        userList.addUser(user);

        userList.saveUsers(context);

        /* End AddItemActivity */
        finish();
    }

    private String randString(int length) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }
}

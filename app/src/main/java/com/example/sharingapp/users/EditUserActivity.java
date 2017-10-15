package com.example.sharingapp.users;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.sharingapp.ItemList;
import com.example.sharingapp.R;

public class EditUserActivity extends AppCompatActivity {

    private UserList userList = new UserList();
    private User user;
    private Context context;
    private UserList activeBorrowersList;
    private ItemList itemList;
    private EditText username;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        username = (EditText) findViewById(R.id.username_et);
        email = (EditText) findViewById(R.id.email_et);
        itemList = new ItemList();


        context = getApplicationContext();
        userList.loadUsers(context);
        itemList.loadItems(context);
        // get intent from HomeActivity
        Intent intent = getIntent();
        int pos = intent.getIntExtra("position", 0);

        user = userList.getUser(pos);

        username.setText(user.getUsername());
        email.setText(user.getEmail());
    }

    private boolean isUserABorrower() {
        for(int i = 0; i < itemList.getSize(); i++) {
            if(itemList.getItem(i).getBorrower() != null) {
                if (user.getId().equals(itemList.getItem(i).getBorrower().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteUser(View view) {
        if(isUserABorrower()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditUserActivity.this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.dialog_title)
                    .setTitle(R.string.user_is_borrower);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    //some code

                } });
            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            userList.removeUser(user);
            userList.saveUsers(context);

         /* End EditItemActivity */
            finish();
        }
    }

    public void saveUser(View view) {
        if(isUserABorrower()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditUserActivity.this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.dialog_title)
                    .setTitle(R.string.user_is_borrower);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    //some code

                } });
            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
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

}

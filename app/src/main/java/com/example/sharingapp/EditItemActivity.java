package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sharingapp.users.User;
import com.example.sharingapp.users.UserAdapter;
import com.example.sharingapp.users.UserList;

import java.util.ArrayList;

/**
 * Edit preexisting item: editing an item consists of deleting the old item and adding a new item
 * with the old item's id.
 * Note: invisible EditText is used to setError for status. For whatever reason I cannot .setError to
 * the status Switch so instead this is done using an additional "invisible" EditText.
 */
public class EditItemActivity extends AppCompatActivity {

    private ItemList item_list = new ItemList();
    private Item item;
    private Context context;

    private Bitmap image;
    private int REQUEST_CODE = 1;
    private ImageView photo;

    private EditText title;
    private EditText maker;
    private EditText description;
    private EditText length;
    private EditText width;
    private EditText height;
//    private EditText borrower;
    private TextView  borrower_tv;
    private Switch status;
    private Spinner users_spinner;
    private UserList userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        userList = new UserList();
        title = (EditText) findViewById(R.id.title);
        maker = (EditText) findViewById(R.id.maker);
        description = (EditText) findViewById(R.id.description);
        length = (EditText) findViewById(R.id.length);
        width = (EditText) findViewById(R.id.width);
        height = (EditText) findViewById(R.id.height);
//        borrower = (EditText) findViewById(R.id.borrower);
        borrower_tv = (TextView) findViewById(R.id.borrower_tv);
        photo = (ImageView) findViewById(R.id.image_view);
        status = (Switch) findViewById(R.id.available_switch);
        context = getApplicationContext();

        users_spinner = (Spinner) findViewById(R.id.users_spinner);
        userList.loadUsers(context);
        UserAdapter adapter = new UserAdapter(context, userList.getUsers(), R.layout.list_user);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        users_spinner.setAdapter(adapter);
        item_list.loadItems(context);
        userSpinnerHandler();
        // get intent from HomeActivity
        Intent intent = getIntent();
        int pos = intent.getIntExtra("position", 0);

        item = item_list.getItem(pos);

        title.setText(item.getTitle());
        maker.setText(item.getMaker());
        description.setText(item.getDescription());

        Dimensions dimensions = item.getDimensions();

        length.setText(dimensions.getLength());
        width.setText(dimensions.getWidth());
        height.setText(dimensions.getHeight());

        String status_str = item.getStatus();
        if (status_str.equals("Borrowed")) {
            status.setChecked(false);
//            borrower.setText(item.getBorrower());
        } else {
            borrower_tv.setVisibility(View.GONE);
            users_spinner.setVisibility(View.GONE);
//            borrower.setVisibility(View.GONE);
        }

        if(item.getBorrower() != null)
            setSelectedBorrower();

        image = item.getImage();
        if (image != null) {
            photo.setImageBitmap(image);
        } else {
            photo.setImageResource(android.R.drawable.ic_menu_gallery);
        }
    }

    private void setSelectedBorrower() {
        for(int i = 0; i< userList.getSize(); i++) {
            if(item.getBorrower().getId().equals(userList.getUser(i).getId())) {
                System.out.println("email: "+ item.getBorrower().getEmail());
                System.out.println("position: " + i);
                users_spinner.setSelection(i);
                break;
            }
        }
    }

    private void userSpinnerHandler() {
        users_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                User borrower = (User) users_spinner.getSelectedItem();
//                item.setBorrower(borrower);
//                System.out.println("username: " + borrower.getUsername());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    public void deletePhoto(View view) {
        image = null;
        photo.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent intent){
        if (request_code == REQUEST_CODE && result_code == RESULT_OK){
            Bundle extras = intent.getExtras();
            image = (Bitmap) extras.get("data");
            photo.setImageBitmap(image);
        }
    }

    public void deleteItem(View view) {
        item_list.removeItem(item);
        item_list.saveItems(context);

         /* End EditItemActivity */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveItem(View view) {

        String title_str = title.getText().toString();
        String maker_str = maker.getText().toString();
        String description_str = description.getText().toString();
        String length_str = length.getText().toString();
        String width_str = width.getText().toString();
        String height_str = height.getText().toString();
//        String borrower_str = borrower.getText().toString();

        Dimensions dimensions = new Dimensions(length_str, width_str, height_str);

        if (title_str.equals("")) {
            title.setError("Empty field!");
            return;
        }

        if (maker_str.equals("")) {
            maker.setError("Empty field!");
            return;
        }

        if (description_str.equals("")) {
            description.setError("Empty field!");
            return;
        }

        if (length_str.equals("")) {
            length.setError("Empty field!");
            return;
        }

        if (width_str.equals("")) {
            width.setError("Empty field!");
            return;
        }

        if (height_str.equals("")) {
            height.setError("Empty field!");
            return;
        }

        // Reuse the item id
        String id = item.getId();
        item_list.removeItem(item);

        Item updated_item = new Item(title_str, maker_str, description_str, dimensions, image, id);

        boolean checked = status.isChecked();
        if (!checked) {
            // means borrowed
            updated_item.setStatus("Borrowed");
//            updated_item.setBorrower(borrower_str);
            // todo
            User borrower = (User) users_spinner.getSelectedItem();
            updated_item.setBorrower(borrower);
        }
        item_list.addItem(updated_item);

        item_list.saveItems(context);

        /* End EditItemActivity */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Checked = Available
     * Unchecked = Borrowed
     */
    public void toggleSwitch(View view){
        if (status.isChecked()) {
            // means was previously borrowed
            // borrower.setVisibility(View.GONE);
            borrower_tv.setVisibility(View.GONE);
            users_spinner.setVisibility(View.GONE);
            item.setBorrower(null);
            item.setStatus("Available");

        } else {
            // means was previously available
            // borrower.setVisibility(View.VISIBLE);
            users_spinner.setVisibility(View.VISIBLE);
            borrower_tv.setVisibility(View.VISIBLE);
        }
    }
}

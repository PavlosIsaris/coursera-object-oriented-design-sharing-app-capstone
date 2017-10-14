package com.example.sharingapp.users;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserList {

    private static ArrayList<User> users;
    private String FILENAME = "user_file.sav";

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        UserList.users = users;
    }

    public ArrayList<User> getAllUsernames() {
        // TODO change
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public int getSize() {
        return users.size();
    }

    public int getIndex(User user) {
        int pos = 0;
        for (User u : users) {
            if (user.getId().equals(u.getId())) {
                return pos;
            }
            pos = pos+1;
        }
        return -1;
    }

    public boolean hasUser(User user) {
        for (User u : users) {
            if (user.getId().equals(u.getId())) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String username) throws Exception {
        int pos = 0;
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
            pos = pos+1;
        }
        throw new Exception("User not found");
    }

    public void loadUsers(Context context) {

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            users = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            users = new ArrayList<User>();
        } catch (IOException e) {
            users = new ArrayList<User>();
        }

        if(users.isEmpty())
            users.add(0, new User("id", "test@dev.com", "Paul The Developer"));
    }

    public void saveUsers(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(users, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserNameAvailable(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
}

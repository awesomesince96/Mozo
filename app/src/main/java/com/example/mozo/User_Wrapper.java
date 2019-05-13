package com.example.mozo;

import java.util.ArrayList;
import java.util.List;

public class User_Wrapper {

   List<User> users;

    public User_Wrapper(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "User_Wrapper{" +
                "users=" + users +
                '}';
    }
}

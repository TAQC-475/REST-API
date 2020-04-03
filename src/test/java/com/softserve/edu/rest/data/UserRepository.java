package com.softserve.edu.rest.data;

import java.util.ArrayList;
import java.util.List;

public final class UserRepository {

    private UserRepository() {
    }

    public static User getDefault() {
        return getAdmin();
    }

    public static User getAdmin() {
        return new User("admin", "qwerty", true).addItem("My data");
    }

    public static User getValidUser() {
        return new User("otlumtc", "qwerty", false);
    }

    public static User notExistingUser() {
        return new User("Anna", "ksjddlfkjddqwerty", false).addItem("My data");
    }

    public static List<User> getExistingUsers(){
        List<User> users = new ArrayList<>();
        return users;
    }
}

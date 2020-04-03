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


    public static List<User> getExistingUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User("vvasylystc", "qwerty", false));
        users.add(new User("kilinatc", "qwerty", false));
        users.add(new User("slototc", "qwerty", false));
        users.add(new User("vbudktc", "qwerty", false));
        users.add(new User("akimatc", "qwerty", false));
        return users;
    }

    public static User notExistingUser() {
        return new User("Anna", "ksjddlfkjddqwerty", false).addItem("My data");
    }

}

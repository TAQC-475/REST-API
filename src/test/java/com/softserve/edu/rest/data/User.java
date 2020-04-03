package com.softserve.edu.rest.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    // private final String ERROR_USER_LOCKED = "ERROR, user locked";
    // private final String ERROR_USER_NOT_FOUND = "ERROR, user not found";
    //
    private String name;
    private String password;
    private boolean isAdmin;
    private List<String> items;

    public User(String name) {
        this.name = name;
        this.password = "";
        this.isAdmin = false;
        this.items = new ArrayList<>();
    }

    // TODO Develop Builder
    public User(String name, String password, boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
        items = new ArrayList<>();
    }

    // setters

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public User addItem(String item) {
        items.add(item);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return isAdmin == user.isAdmin &&
                Objects.equals(name, user.name) &&
                //Objects.equals(password, user.password) &&
                Objects.equals(items, user.items);
    }

    public boolean equalName(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return name.equalsIgnoreCase(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, isAdmin, items);
    }

    // getters

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "User [name=" + name
                //+ ", password=" + password
                + ", isAdmin=" + isAdmin
                + ", items=" + items + "]";
    }
}
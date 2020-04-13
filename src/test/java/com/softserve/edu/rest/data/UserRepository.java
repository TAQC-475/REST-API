package com.softserve.edu.rest.data;

import com.softserve.edu.rest.tools.EntityUtils;

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

    public static User getAdminVasya() {
        return new User("Vasya", "qwerty", true);
    }

    public static User getUserVasya() {
        return new User("Vasya", "qwerty", false);
    }

    public static User getAdminDana() {
        return new User("Dana", "qwerty", true);
    }

    public static User getUserDana() {
        return new User("Dana", "qwerty", false);
    }

    public static User getNotExistedAdmin() {
        return new User("Puhlyash", "qwerty", true).addItem("My data");
    }

    public static User getValidUser() {
        return new User("otlumtc", "qwerty", false);
    }
    public static User getAkimatcUser() {
        return new User("akimatc", "qwerty", false);
    }

    public static User getUserWithWrongPassword() {
        return new User("otlumtc", "wrong", false);
    }

    public static User getUserWithNewPassword(String password) {
        return new User("otlumtc", password, false);
    }

    public static User getFedorUser() {
        return new User("Fedor", "qwerty", false);
    }

    public static User getCorrectNewUser(int nameCount, int passwordCount) {
        return new User(EntityUtils.randomAlphaNumeric(nameCount), EntityUtils.randomAlphaNumeric(passwordCount), false);
    }

    public static User notExistingUser() {
        return new User("Anna", "ksjddlfkjddqwerty", false).addItem("My data");
    }

    public static List<User> getExistingUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("kilinatc", "qwerty", false));
        users.add(new User("akimatc", "qwerty", false));
        users.add(new User("OKonokhtc", "qwerty", false));
        users.add(new User("slototc", "qwerty", false));
        users.add(new User("khalaktc", "qwerty", false));
        return users;
    }

    public static User getNonExistingUser() {
        return new User("nonexistinguser", "qwerty", false);
    }

    public static User getNonExistingAdmin() {
        return new User("nonexistingadmin", "qwerty", true);
    }

    public static List<User> getNonExistingUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("kaban", "qwerty", false));
        users.add(new User("puhlyash", "qwerty", false));
        users.add(new User("sraka", "qwerty", false));
        users.add(new User("malyshka", "qwerty", false));
        users.add(new User("katakombic", "qwerty", false));
        return users;
    }

    public static List<User> getNonExistingAdmins() {
        List<User> admins = new ArrayList<>();
        //admins.add(new User("kabanAdmin", "qwerty", true));
        //admins.add(new User("puhlyashAdmin", "qwerty", true));
        admins.add(new User("srakaAdmin", "qwerty", true));
        admins.add(new User("malyshkaAdmin", "qwerty", true));
        admins.add(new User("katakombicAdmin", "qwerty", true));
        return admins;
    }
}

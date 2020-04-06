package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class UsersTestData {
    @DataProvider(name = "existingUsersDataProvider")
    public Object[][] getExistingUsers(){
        Object[][] objects = {{UserRepository.getAdmin(), UserRepository.getExistingUsers()}};
        return objects;
    }

    @DataProvider(name = "nonExistingUsersDataProvider")
    public Object[][] getNonExistingUsers(){
        Object[][] objects = {{UserRepository.getAdmin(), UserRepository.getNonExistingUsers()}};
        return objects;
    }

    @DataProvider(name = "nonExistingAdminsDataProvider")
    public Object[][] getNonExistingAdmins(){
        Object[][] objects = {{UserRepository.getAdmin(), UserRepository.getNonExistingAdmins()}};
        return objects;
    }

    @DataProvider(name = "existingUserDataProvider")
    public Object[][] getExistingUser(){
        Object[][] user = {{UserRepository.getAdmin(), UserRepository.getValidUser()}};
        return user;
    }

    @DataProvider(name = "existingAdminDataProvider")
    public Object[] getExistingAdmin(){
        Object[] admin = {UserRepository.getAdmin()};
        return admin;
    }

    @DataProvider(name = "nonExistingUserDataProvider")
    public Object[][] getNonExistingUser(){
        Object[][] user = {{UserRepository.getAdmin(), UserRepository.getNonExistingUser()}};
        return user;
    }

    @DataProvider(name = "nonExistingAdminDataProvider")
    public Object[][] getNonExistingAdmin(){
        Object[][] admin = {{UserRepository.getAdmin(), UserRepository.getNonExistingAdmin()}};
        return admin;
    }
}

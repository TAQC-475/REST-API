package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UserService;
import com.softserve.edu.rest.services.UsersService;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class LoginUsersTest extends RestTestRunner{
    @DataProvider(name = "existingUsersDataProvider")
    public Object[] getExistingUsers(){
        Object[] objects = {UserRepository.getExistingUsers()};
        return objects;
    }

    @Test(dataProvider = "existingUsersDataProvider")
    public void loginExistingUsersTest(List<User> existingUsers){
        UserService usersService = new LoginService()
                .successfulUsersLogin(existingUsers);
    }

}

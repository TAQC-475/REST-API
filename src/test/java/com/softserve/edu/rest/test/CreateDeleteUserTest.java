package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UsersService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateDeleteUserTest {
    SoftAssert softAssert = new SoftAssert();

    @DataProvider
    public Object[][] createUserData() {
        return new Object[][]{{UserRepository.getAdmin(),
                UserRepository.getValidUser(),
                UserRepository.getValidUser()}};
    }

    @Test(dataProvider = "createUserData",
            description = "Check if admin could create user with all valid fields")
    public void createUser(User adminUser, User validUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(validUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }

//    @DataProvider
//    public Object[][] userData(){
//        return new Object[][]{{UserRepository.getValidUser()}};
//    }
//
//    @Test(description = "Check if user can change his password")
//    public void updateUserPassword(User user){
//        LoginService login = new
//
//    }
}


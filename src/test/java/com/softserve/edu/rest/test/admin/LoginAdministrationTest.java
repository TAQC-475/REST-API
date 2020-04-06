package com.softserve.edu.rest.test.admin;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UsersService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginAdministrationTest {
    @DataProvider
    public Object[][] userExist() {
        return new Object[][]{{UserRepository.getAdmin(), UserRepository.getValidUser()}};
    }

    @Test(dataProvider = "userExist")
    public void checkExistUser(User adminUser, User validUser) {
//    Assert.assertTrue(new LoginService()
//        .successfulAdmin(adminUser)
//        .gotoUsersService()
//        .isUserPresent(validUser));
//        UsersService usersService = new LoginService()
//                .successfulAdminLogin(adminUser)
//                .gotoUsersService();

//        Assert.assertTrue(usersService.isUserPresent(validUser));
//        usersService.gotoAdministrationService();
    }

    public void checkExistUsersLoggined(User adminUser){}
}

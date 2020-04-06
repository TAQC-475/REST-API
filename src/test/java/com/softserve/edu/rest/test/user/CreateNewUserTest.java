package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UsersService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateNewUserTest {

    @DataProvider
    public Object[][] createUserData() {
        return new Object[][]{{UserRepository.getAdmin(),
                UserRepository.getFedorUser(),
                UserRepository.getFedorUser()}};
    }

    @Test(dataProvider = "createUserData",
            description = "Check if admin could create user with all valid fields")
    public void createUser(User adminUser, User newUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }
}
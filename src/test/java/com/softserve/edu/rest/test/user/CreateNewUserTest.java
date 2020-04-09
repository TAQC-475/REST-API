package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.CreateUserData;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UsersService;
import io.qameta.allure.Epic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Create new user tests")
public class CreateNewUserTest {
    public static final Logger logger = LoggerFactory.getLogger(CreateNewUserTest.class);

    @Test(dataProvider = "createUserData", dataProviderClass = CreateUserData.class,
            description = "Check if admin could create user with all valid fields")
    public void createUser(User adminUser, User newUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }

    @Test(dataProvider = "userWithEmptyNameData", dataProviderClass = CreateUserData.class,
            description = "Check if admin could create user with empty name field")
    public void createUserWithEmptyName(User adminUser, User newUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }

    @Test(dataProvider = "userWithEmptyPasswordData", dataProviderClass = CreateUserData.class,
            description = "Check if admin could create user with empty password field")
    public void createUserWithEmptyPassword(User adminUser, User newUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }

    @Test(dataProvider = "userWithEmptyNameAndPasswordData", dataProviderClass = CreateUserData.class,
            description = "Check if admin could create user with all empty fields")
    public void createUserWithEmptyNameAndPassword(User adminUser, User newUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }
}
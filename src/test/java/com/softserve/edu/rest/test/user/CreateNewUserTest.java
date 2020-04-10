package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.CreateUserData;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UsersService;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

@Epic("Create new user tests")
public class CreateNewUserTest extends UserTestRunner {
    private static Logger LOGGER = Logger.getLogger(CreateNewUserTest.class);

    @Description("Check if admin could create user with all valid fields")
    @Parameters({"Admin login", "Create user", "Check is user present"})
    @Test(dataProvider = "createUserData", dataProviderClass = CreateUserData.class)
    public void createUser(User adminUser, User newUser, User expectedUser) {
        LOGGER.info("Creating valid user with parameters: adminUser = " + adminUser.toString()
                + " newUser" + newUser.toString());

        UsersService actualUser = new LoginService()
                .loginAndCreateUser(adminUser, newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
        LOGGER.info("Finished creating user");
    }

    @Description("Check if admin could create user with empty name field")
    @Parameters({"Admin login", "Create user", "Check is user present"})
    @Test(dataProvider = "userWithEmptyNameData", dataProviderClass = CreateUserData.class)
    public void createUserWithEmptyName(User adminUser, User newUser, User expectedUser) {
        LOGGER.info("Creating invalid user with parameters: adminUser = " + adminUser.toString()
                + " newUser" + newUser.toString());

        UsersService actualUser = new LoginService()
                .loginAndCreateUser(adminUser, newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
        LOGGER.info("Finished creating user");
    }

    @Description("Check if admin could create user with empty password field")
    @Parameters({"Admin login", "Create user", "Check is user present"})
    @Test(dataProvider = "userWithEmptyPasswordData", dataProviderClass = CreateUserData.class)
    public void createUserWithEmptyPassword(User adminUser, User newUser, User expectedUser) {
        LOGGER.info("Creating invalid user with parameters: adminUser = " + adminUser.toString()
                + " newUser" + newUser.toString());

        UsersService actualUser = new LoginService()
                .loginAndCreateUser(adminUser, newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
        LOGGER.info("Finished creating user");
    }

    @Description("Check if admin could create user with all empty fields")
    @Parameters({"Admin login", "Create user", "Check is user present"})
    @Test(dataProvider = "userWithEmptyNameAndPasswordData", dataProviderClass = CreateUserData.class)
    public void createUserWithEmptyNameAndPassword(User adminUser, User newUser, User expectedUser) {
        LOGGER.info("Creating invalid user with parameters: adminUser = " + adminUser.toString()
                + " newUser" + newUser.toString());

        UsersService actualUser = new LoginService()
                .loginAndCreateUser(adminUser, newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
        LOGGER.info("Finished creating user");
    }
}
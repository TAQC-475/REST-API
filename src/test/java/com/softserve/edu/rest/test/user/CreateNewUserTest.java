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

@Epic("Create new user tests")
public class CreateNewUserTest extends UserTestRunner {

    @Description("Check if admin could create user with all valid fields")
    @Parameters({"Admin login", "Create user", "Check is user present"})
    @Test(dataProvider = "createUserData", dataProviderClass = CreateUserData.class)
    public void createUser(User adminUser, User newUser, User expectedUser) {
        LOGGER.info("Creating valid user with parameters: " + newUser.toString());

        UsersService actualUser = new LoginService()
                .loginAndCreateUser(adminUser, newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser),
                String.format("User with name: %s, password: %s did not created", newUser.getName(), newUser.getPassword()));
        LOGGER.info("Finished creating user \n");
    }

    @Description("Check if admin could create user with empty name field")
    @Parameters({"Admin login", "Create user", "Check is user present"})
    @Test(dataProvider = "userWithEmptyNameData", dataProviderClass = CreateUserData.class)
    public void createUserWithEmptyName(User adminUser, User newUser, User expectedUser) {
        LOGGER.info("Creating invalid user with parameters: " + newUser.toString());

        UsersService actualUser = new LoginService()
                .loginAndCreateUser(adminUser, newUser)
                .gotoUsersService();

        Assert.assertFalse(actualUser.isUserPresent(expectedUser),
                String.format("User with name: %s, password: %s did not created", newUser.getName(), newUser.getPassword()));
        LOGGER.info("Finished creating user \n");
    }

    @Description("Check if admin could create user with empty password field")
    @Parameters({"Admin login", "Create user", "Check is user present"})
    @Test(dataProvider = "userWithEmptyPasswordData", dataProviderClass = CreateUserData.class)
    public void createUserWithEmptyPassword(User adminUser, User newUser, User expectedUser) {
        LOGGER.info("Creating invalid user with parameters: " + newUser.toString());

        UsersService actualUser = new LoginService()
                .loginAndCreateUser(adminUser, newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser),
                String.format("User with name: %s, password: %s did not created", newUser.getName(), newUser.getPassword()));
        LOGGER.info("Finished creating user \n");
    }

    @Description("Check if admin could create user with all empty fields")
    @Parameters({"Admin login", "Create user", "Check is user present"})
    @Test(dataProvider = "userWithEmptyNameAndPasswordData", dataProviderClass = CreateUserData.class)
    public void createUserWithEmptyNameAndPassword(User adminUser, User newUser, User expectedUser) {
        LOGGER.info("Creating invalid user with parameters: " + newUser.toString());

        UsersService actualUser = new LoginService()
                .loginAndCreateUser(adminUser, newUser)
                .gotoUsersService();

        Assert.assertFalse(actualUser.isUserPresent(expectedUser),
                String.format("User with name: %s, password: %s did not created", newUser.getName(), newUser.getPassword()));
        LOGGER.info("Finished creating user \n");
    }
}
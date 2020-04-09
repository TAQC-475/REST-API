package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UsersService;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("Create new user tests")
public class CreateNewUserTest {
    public static final Logger logger = LoggerFactory.getLogger(CreateNewUserTest.class);

    @DataProvider
    public Object[][] createUserData() {
        User newUser = UserRepository.getCorrectNewUser(6,5);
        return new Object[][]{{UserRepository.getAdmin(),
                newUser,
                newUser}};
    }

    @Test(dataProvider = "createUserData",
            description = "Check if admin {adminUser}, could create user with all valid fields")
    public void createUser(User adminUser, User newUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }



    @DataProvider
    public Object[][] userWithEmptyNameData() {
        User newUser = UserRepository.getCorrectNewUser(0,5);
        return new Object[][]{{UserRepository.getAdmin(),
                newUser,
                newUser}};
    }

    @Test(dataProvider = "userWithEmptyNameData",
            description = "Check if admin could create user with empty name field")
    public void createUserWithEmptyName(User adminUser, User newUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }


    @DataProvider
    public Object[][] userWithEmptyPasswordData() {
        User newUser = UserRepository.getCorrectNewUser(6,0);
        return new Object[][]{{UserRepository.getAdmin(),
                newUser,
                newUser}};
    }

    @Test(dataProvider = "userWithEmptyPasswordData",
            description = "Check if admin could create user with empty password field")
    public void createUserWithEmptyPassword(User adminUser, User newUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }


    @DataProvider
    public Object[][] userWithEmptyNameAndPasswordData() {
        User newUser = UserRepository.getCorrectNewUser(0,0);
        return new Object[][]{{UserRepository.getAdmin(),
                newUser,
                newUser}};
    }

    @Test(dataProvider = "userWithEmptyNameAndPasswordData",
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
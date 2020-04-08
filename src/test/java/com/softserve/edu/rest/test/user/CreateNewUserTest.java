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

    @Description("Check if admin {adminUser}, could create user with all valid fields")
    @Test(dataProvider = "createUserData")
    public void createUser(User adminUser, User newUser, User expectedUser) {
        UsersService actualUser = new LoginService()
                .successfulAdminLogin(adminUser)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoUsersService();

        Assert.assertTrue(actualUser.isUserPresent(expectedUser));
    }


}
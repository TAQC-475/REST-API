package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UserService;
import com.softserve.edu.rest.services.UsersService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Random;

public class CreateDeleteUserTest {
    SoftAssert softAssert = new SoftAssert();

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

    @DataProvider
    public Object[][] userData() {
        return new Object[][]{{
                UserRepository.getFedorUser(),
                UserRepository.getFedorUser(),
                UserRepository.getFedorWithNewPassword(),
                UserRepository.getFedorWithNewPassword()}};
    }

    @Test(dataProvider = "userData", description = "Check if user can change his password")
    public void updateUserPassword(User user, User oldPassword, User newPassword, User sameUser) {
        SimpleEntity logout = new LoginService()
                .successfulUserLogin(user)
                .changePassword(oldPassword, newPassword)
                .goToLoginService()
                .successfulLogout(ApplicationState.get().getLastLoggined());

        softAssert.assertTrue(UserService.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulUserLogin(sameUser)
                .goToLoginService()
                .successfulLogout(ApplicationState.get().getLastLoggined());

        softAssert.assertTrue(UserService.isUserActionSuccessful(login));

        softAssert.assertAll();
    }

//    public void test123() {
//        String newPassword = RandomStringUtils.randomAlphanumeric(255);
//        newPassword = newPassword.concat("%");
//    }



}
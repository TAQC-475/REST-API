package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UsersService;
import com.softserve.edu.rest.tools.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
    public Object[][] updateUserPasswordData() {
        return new Object[][]{{
                UserRepository.getFedorUser(),
                UserRepository.getFedorUser(),
                EntityUtils.randomAlphaNumeric(7),
                UserRepository.getFedorWithNewPassword()}};
    }

    @Test(dataProvider = "updateUserPasswordData", description = "Check if user can change his password")
    public void updateUserPassword(User user, User oldPassword, String newPassword, User sameUser) {
        SimpleEntity logout = new LoginService()
                .successfulUserLogin(user)
                .changePassword(oldPassword, newPassword)
                .goToLoginService()
                .successfulLogout(ApplicationState.get().getLastLoggined());

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulUserLogin(sameUser)
                .goToLoginService()
                .successfulLogout(ApplicationState.get().getLastLoggined());

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login));

        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] updateUserPasswordToEmptyData() {
        return new Object[][]{{
                UserRepository.getFedorUser(),
                UserRepository.getFedorUser(),
                EntityUtils.randomAlphaNumeric(255),
                UserRepository.getFedorWithNewPassword()}};
    }

//    @Test(dataProvider = "updateUserPasswordToEmptyData")
//    public void updateUserPasswordToEmpty(User user, User oldPassword, String newPassword, User sameUser) {
//        SimpleEntity logout = new LoginService()
//                .successfulUserLogin(user)
//                .changePassword(oldPassword, newPassword)
//                .goToLoginService()
//                .successfulLogout(ApplicationState.get().getLastLoggined());
//
//        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));
//
//        SimpleEntity login = new LoginService()
//                .successfulUserLogin(sameUser)
//                .goToLoginService()
//                .successfulLogout(ApplicationState.get().getLastLoggined());
//
//        softAssert.assertTrue(EntityUtils.isUserActionUnSuccessful(login));
//
//        softAssert.assertAll();
//    }


}
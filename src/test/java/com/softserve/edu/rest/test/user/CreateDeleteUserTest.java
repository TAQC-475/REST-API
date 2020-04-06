package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.tools.EntityUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateDeleteUserTest {
    SoftAssert softAssert = new SoftAssert();

    @DataProvider
    public Object[][] updateUserPasswordData() {
        return new Object[][]{{
                UserRepository.getFedorUser(),
                UserRepository.getFedorUser(),
                EntityUtils.randomAlphaNumeric(7),
                UserRepository.getFedorWithNewPassword()}};
    }


    @Test(dataProvider = "updateUserPasswordData",
            description = "Check if user can change his password")
    public void updateUserPassword(User user, String oldPassword, String newPassword, User sameUser) {
        SimpleEntity logout = new LoginService()
                .successfulUserLogin(user)
                .changePassword(oldPassword, newPassword)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLoggined());

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulUserLogin(sameUser)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLoggined());

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login));

        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] updateUserPasswordToEmptyData() {
        String oldPassword = "qwerty";
        String newPassword = EntityUtils.randomAlphaNumeric(0);
        return new Object[][]{{
                UserRepository.getFedorUser(oldPassword),
                UserRepository.getFedorUser(newPassword),
                newPassword,
                UserRepository.getFedorUser(newPassword)}};
    }

    @Test(dataProvider = "updateUserPasswordToEmptyData")
    public void updateUserPasswordToEmpty(User user, String oldPassword, String newPassword, User sameUser) {
        SimpleEntity logout = new LoginService()
                .successfulUserLogin(user)
                .changePassword(oldPassword, newPassword)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLoggined());

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulUserLogin(sameUser)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLoggined());

        softAssert.assertTrue(EntityUtils.isUserActionUnSuccessful(login));

        softAssert.assertAll();
    }


//    @Test
//    public void updateUserPasswordToEmptyField(User user, String oldPassword, String newPassword, User sameUser) {
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
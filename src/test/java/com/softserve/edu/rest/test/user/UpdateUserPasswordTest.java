package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.UserPasswordData;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Epic;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Change password tests")
public class UpdateUserPasswordTest extends UserTestRunner {
    SoftAssert softAssert = new SoftAssert();

    @Test(dataProvider = "updateUserPasswordToEmptyData", dataProviderClass = UserPasswordData.class,
            description = "Check if User can change his password to an empty")
    public void updateUserPasswordToEmpty(User user, User oldPassword, String newPassword, User sameUser) {

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login));

        softAssert.assertAll();
    }

    @Test(dataProvider = "updateUserPasswordToBeforeTheLimitData", dataProviderClass = UserPasswordData.class,
            description = "Check if User can change his password to 2 symbols")
    public void updatePasswordToBeforeTheMinimumLimitSymbols(User user, User oldPassword, String newPassword,
                                                             User sameUser) {

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login));

        softAssert.assertAll();
    }

    @Test(dataProvider = "updateUserPasswordToToLowerLimitData", dataProviderClass = UserPasswordData.class,
            description = "Check if User can change his password to valid minimum symbols")
    public void updatePasswordToLowerLimitSymbols(User user, User oldPassword, String newPassword, User sameUser) {

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login));

        softAssert.assertAll();
    }

    @Test(dataProvider = "updateUserPasswordData", dataProviderClass = UserPasswordData.class,
            description = "Check if user can change his password to valid password")
    public void updateUserPassword(User user, User oldPassword, String newPassword, User sameUser) {

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login));

        softAssert.assertAll();
    }

    @Test(dataProvider = "updateUserPasswordToUpperLimitData", dataProviderClass = UserPasswordData.class,
            description = "Check if User can change his password to 25 symbols")
    public void updateUserPasswordToMaximumLimitSymbols(User user, User oldPassword, String newPassword,
                                                        User sameUser) {
        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login));

        softAssert.assertAll();
    }

    @Test(dataProvider = "updateUserPasswordToBeyondTheLimitData", dataProviderClass = UserPasswordData.class)
    public void updateUserPasswordToBeyondTheLimitSymbols(User user, User oldPassword, String newPassword,
                                                          User sameUser) {
        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login));

        softAssert.assertAll();
    }
}
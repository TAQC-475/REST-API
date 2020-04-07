package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.data.dataproviders.UserPasswordData;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.tools.EntityUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateUserPasswordTest {
    GuestService guestService;
    SoftAssert softAssert = new SoftAssert();

//    @AfterMethod
//    public void afterMethod() {
//        guestService.resetServiceToInitialState();
//    }

    @Test(dataProvider = "updateUserPasswordToEmptyData", dataProviderClass = UserPasswordData.class,
            description = "Check if User can change his password to an empty")
    public void updateUserPasswordToEmpty(User user, User oldPassword, String newPassword, User sameUser) {

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionUnSuccessful(login));

        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] updateUserPasswordToBeforeTheLimitData() {
        String newPassword = EntityUtils.randomAlphaNumeric(2);
//        newPassword = newPassword.concat("%^");
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @Test(dataProvider = "updateUserPasswordToBeforeTheLimitData",
            description = "Check if User can change his password to 2 symbols")
    public void updatePasswordToBeforeTheMinimumLimitSymbols(User user, User oldPassword, String newPassword,
                                                             User sameUser) {

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionUnSuccessful(login));

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

        softAssert.assertTrue(EntityUtils.isUserActionUnSuccessful(login));

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

        softAssert.assertTrue(EntityUtils.isUserActionUnSuccessful(login));

        softAssert.assertAll();
    }
}
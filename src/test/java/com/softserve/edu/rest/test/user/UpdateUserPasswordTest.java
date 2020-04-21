package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.UserPasswordData;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Epic("Change password tests")
public class UpdateUserPasswordTest extends UserTestRunner {

    @Description("Check if User can change his password to an empty")
    @Parameters({"User login", "User with old password", "New password", "Login with new password"})
    @Test(dataProvider = "updateUserPasswordToEmptyData", dataProviderClass = UserPasswordData.class)
    public void updateUserPasswordToEmpty(User user, User oldPassword, String newPassword, User sameUser) {
        LOGGER.info("Changing user password to an empty with parameters: old password = " + oldPassword.getPassword()
                + "; new password = " + newPassword);

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout),
                String.format("Old password - %s did not changed to new password - %s.", oldPassword, newPassword));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login),
                String.format("User could not enter with new password like - %s", newPassword));

        softAssert.assertAll();
        LOGGER.info("Finished changing password \n");
    }

    @Description("Check if User can change his password to minimum invalid symbols")
    @Parameters({"User login", "User with old password", "New password", "Login with new password"})
    @Test(dataProvider = "updateUserPasswordToBeforeTheLimitData", dataProviderClass = UserPasswordData.class)
    public void updatePasswordToInvalidMinimumSymbols(User user, User oldPassword, String newPassword,
                                                      User sameUser) {
        LOGGER.info("Changing user password to invalid minimum with parameters: old password = " + oldPassword.getPassword()
                + "; new password = " + newPassword);

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout),
                String.format("Old password - %s did not changed to new password - %s.", oldPassword, newPassword));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login),
                String.format("User could not enter with new password like - %s", newPassword));

        softAssert.assertAll();
        LOGGER.info("Finished changing password \n");
    }

    @Description("Check if User can change his password to minimum valid symbols")
    @Parameters({"User login", "User with old password", "New password", "Login with new password"})
    @Test(dataProvider = "updateUserPasswordToMinimumValidData", dataProviderClass = UserPasswordData.class)
    public void updatePasswordToValidMinimumSymbols(User user, User oldPassword, String newPassword, User sameUser) {
        LOGGER.info("Changing user password to valid minimum with parameters: old password = " + oldPassword.getPassword()
                + "; new password = " + newPassword);

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout),
                String.format("Old password - %s did not changed to new password - %s.", oldPassword, newPassword));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login),
                String.format("User could not enter with new password like - %s", newPassword));

        softAssert.assertAll();
        LOGGER.info("Finished changing password \n");
    }

    @Description("Check if user can change his password to valid password")
    @Parameters({"User login", "User with old password", "New password", "Login with new password"})
    @Test(dataProvider = "updateUserPasswordData", dataProviderClass = UserPasswordData.class)
    public void updateUserPassword(User user, User oldPassword, String newPassword, User sameUser) {
        LOGGER.info("Changing user password to valid with parameters: old password = " + oldPassword.getPassword()
                + "; new password = " + newPassword);

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout),
                String.format("Old password - %s did not changed to new password - %s.", oldPassword, newPassword));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login),
                String.format("User could not enter with new password like - %s", newPassword));

        softAssert.assertAll();
        LOGGER.info("Finished changing password \n");
    }

    @Description("Check if User can change his password to maximum valid symbols")
    @Parameters({"User login", "User with old password", "New password", "Login with new password"})
    @Test(dataProvider = "updateUserPasswordToUpperLimitData", dataProviderClass = UserPasswordData.class)
    public void updateUserPasswordToMaximumValidSymbols(User user, User oldPassword, String newPassword,
                                                        User sameUser) {
        LOGGER.info("Changing user password to valid maximum with parameters: old password = " + oldPassword.getPassword()
                + "; new password = " + newPassword);

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout),
                String.format("Old password - %s did not changed to new password - %s.", oldPassword, newPassword));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login),
                String.format("User could not enter with new password like - %s", newPassword));

        softAssert.assertAll();
        LOGGER.info("Finished changing password \n");
    }

    @Description("Check if User can change his password to maximum invalid symbols")
    @Parameters({"User login", "User with old password", "New password", "Login with new password"})
    @Test(dataProvider = "updateUserPasswordToMaximumInvalidData", dataProviderClass = UserPasswordData.class)
    public void updateUserPasswordToMaximumInvalidSymbols(User user, User oldPassword, String newPassword,
                                                          User sameUser) {
        LOGGER.info("Changing user password to invalid maximum with parameters: old password = " + oldPassword.getPassword()
                + "; new password = " + newPassword);

        SimpleEntity logout = new LoginService()
                .changePasswordAndLogOut(user, oldPassword, newPassword);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(logout),
                String.format("Old password - %s did not changed to new password - %s.", oldPassword, newPassword));

        SimpleEntity login = new LoginService()
                .successfulLoginAndLogout(sameUser);

        softAssert.assertTrue(EntityUtils.isUserActionSuccessful(login),
                String.format("User could not enter with new password like - %s", newPassword));

        softAssert.assertAll();
        LOGGER.info("Finished changing password \n");
    }
}
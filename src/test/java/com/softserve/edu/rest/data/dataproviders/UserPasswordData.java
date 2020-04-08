package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.tools.EntityUtils;
import org.testng.annotations.DataProvider;

public class UserPasswordData {

    @DataProvider
    public Object[][] updateUserPasswordToEmptyData() {
        String newPassword = EntityUtils.randomAlphaNumeric(0);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordToBeforeTheLimitData() {
        String newPassword = EntityUtils.randomAlphaNumeric(2);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordToToLowerLimitData() {
        String newPassword = EntityUtils.randomAlphaNumeric(5);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordData() {
        String newPassword = EntityUtils.randomAlphaNumeric(17);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordToUpperLimitData() {
        String newPassword = EntityUtils.randomAlphaNumeric(25);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordToBeyondTheLimitData() {
        String newPassword = EntityUtils.randomAlphaNumeric(36);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }
}

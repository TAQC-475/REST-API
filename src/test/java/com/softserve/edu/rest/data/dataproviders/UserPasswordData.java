package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.tools.EntityUtils;
import org.testng.annotations.DataProvider;

public class UserPasswordData {
    private static final int EMPTY_FIELD = 0;
    private static final int LOWEST_INVALID_SYMBOLS_COUNT = 2;
    private static final int LOWEST_VALID_SYMBOLS_COUNT = 5;
    private static final int VALID_SYMBOLS_COUNT = 15;
    private static final int HIGHEST_VALID_SYMBOLS_COUNT = 25;
    private static final int HIGHEST_INVALID_SYMBOLS_COUNT = 36;

    @DataProvider
    public Object[][] updateUserPasswordToEmptyData() {
        String newPassword = EntityUtils.randomAlphaNumeric(EMPTY_FIELD);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordToBeforeTheLimitData() {
        String newPassword = EntityUtils.randomAlphaNumeric(LOWEST_INVALID_SYMBOLS_COUNT);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordToToLowerLimitData() {
        String newPassword = EntityUtils.randomAlphaNumeric(LOWEST_VALID_SYMBOLS_COUNT);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordData() {
        String newPassword = EntityUtils.randomAlphaNumeric(VALID_SYMBOLS_COUNT);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordToUpperLimitData() {
        String newPassword = EntityUtils.randomAlphaNumeric(HIGHEST_VALID_SYMBOLS_COUNT);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }

    @DataProvider
    public Object[][] updateUserPasswordToBeyondTheLimitData() {
        String newPassword = EntityUtils.randomAlphaNumeric(HIGHEST_INVALID_SYMBOLS_COUNT);
        return new Object[][]{{
                UserRepository.getValidUser(),
                UserRepository.getValidUser(),
                newPassword,
                UserRepository.getUserWithNewPassword(newPassword)}};
    }
}
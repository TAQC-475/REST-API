package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class CreateUserData {
    private static final int VALID_NAME_SYMBOLS_COUNT = 6;
    private static final int VALID_PASSWORD_SYMBOLS_COUNT = 5;
    private static final int EMPTY_FIELD = 0;


    @DataProvider
    public Object[][] createUserData() {
        User newUser = UserRepository.getCorrectNewUser(VALID_NAME_SYMBOLS_COUNT, VALID_PASSWORD_SYMBOLS_COUNT);
        return new Object[][]{{UserRepository.getAdmin(),
                newUser,
                newUser}};
    }

    @DataProvider
    public Object[][] userWithEmptyNameData() {
        User newUser = UserRepository.getCorrectNewUser(EMPTY_FIELD, VALID_PASSWORD_SYMBOLS_COUNT);
        return new Object[][]{{UserRepository.getAdmin(),
                newUser,
                true}};
    }

    @DataProvider
    public Object[][] userWithEmptyPasswordData() {
        User newUser = UserRepository.getCorrectNewUser(VALID_NAME_SYMBOLS_COUNT, EMPTY_FIELD);
        return new Object[][]{{UserRepository.getAdmin(),
                newUser,
                newUser}};
    }

    @DataProvider
    public Object[][] userWithEmptyNameAndPasswordData() {
        User newUser = UserRepository.getCorrectNewUser(EMPTY_FIELD, EMPTY_FIELD);
        return new Object[][]{{UserRepository.getAdmin(),
                newUser,
                true}};
    }
}
package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateNewUserTest {
    @Test
    private void createNewUser(){
        boolean result = new LoginService()
            .successfulAdminLogin(UserRepository.getAdmin())
            .gotoManageUserService()
            .createUser(UserRepository.notExistingUser())
            .gotoUsersService()
            .isUserPresent(UserRepository.notExistingUser());

        Assert.assertTrue(result);
}
}

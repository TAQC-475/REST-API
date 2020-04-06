package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveUserTest {

    @Test
    public void removeUserTest(){
        boolean actual = new LoginService()
            .successfulAdminLogin(UserRepository.getAdmin())
            .gotoManageUserService()
            .createUser(UserRepository.notExistingUser())
            .gotoManageUserService()
            .removeUser(UserRepository.notExistingUser())
            .gotoUsersService()
            .isUserPresent(UserRepository.notExistingUser());

        Assert.assertFalse(actual);
    }

}

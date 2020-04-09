package com.softserve.edu.rest.test.admin;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateAdminTest {

    @Test
    public void adminTest() {
        boolean actual = new LoginService()
            .successfulAdminLogin(UserRepository.getAdmin())
            .gotoManageUserService()
            .createUser(UserRepository.getAdminVasya())
            .gotoManageUserService()
            .removeUser(UserRepository.getAdminVasya())
            .gotoManageUserService()
            .createUser(UserRepository.getUserVasya())
            .goToLoginService()
            .successfulLogout(ApplicationState.get().getLastLoggined())
            .gotoLoginService()
            .successfulAdminLogin(UserRepository.getUserVasya())
            .gotoManageUserService()
            .createUser(UserRepository.getNonExistingUser())
            .gotoUsersService()
            .isUserPresent(UserRepository.getNonExistingUser());

        Assert.assertTrue(actual);

    }



}

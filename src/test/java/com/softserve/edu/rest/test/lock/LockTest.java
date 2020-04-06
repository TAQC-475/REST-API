package com.softserve.edu.rest.test.lock;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LockService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LockTest {

    @DataProvider
    public Object[][] lockUser() {
        return new Object[][]{
                { UserRepository.getAdmin(), UserRepository.getUserDana() }
        };
    }

    @DataProvider
    public Object[][] lockAdmin() {
        return new Object[][]{
                { UserRepository.getAdmin(), UserRepository.getAdminVasya() }
        };
    }

    @Test(dataProvider = "lockUser", priority = 1)
    public void lockUserFromAdmin(User admin, User simpleUser) {
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .lockUser(simpleUser);

        Assert.assertTrue(adminService.isUserLocked(simpleUser));
    }

    @Test(dataProvider = "lockAdmin", priority = 2)
    public void lockAdminFromAdmin(User admin, User someAdmin) {
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .lockUser(someAdmin);

        Assert.assertTrue(adminService.isUserLocked(someAdmin));
    }

    @Test(dataProvider = "lockUser", priority = 2)
    public void unlockUserFromAdmin(User admin, User someUser) {
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .unlockUser(someUser);

        Assert.assertFalse(adminService.isUserLocked(someUser));
    }



}

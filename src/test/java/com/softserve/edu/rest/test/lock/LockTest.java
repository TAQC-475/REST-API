package com.softserve.edu.rest.test.lock;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.AdministrationService;
import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.services.LockService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LockTest {

    @BeforeSuite
    public void createExtraUsers() {
        new GuestService().resetServiceToInitialState();
        new LoginService()
                .successfulAdminLogin(UserRepository.getAdmin())
                .gotoManageUserService()
                .createUser(UserRepository.getUserDana())
                .gotoManageUserService()
                .createUser(UserRepository.getAdminVasya());
    }

    @DataProvider
    public Object[][] lockUser() {
        return new Object[][]{
                {UserRepository.getAdmin(), UserRepository.getUserDana()}
        };
    }

    @DataProvider
    public Object[][] Admin() {
        return new Object[][]{
                {UserRepository.getAdmin()}
        };
    }

    @DataProvider
    public Object[][] lockAdminVasya() {
        return new Object[][]{
                {UserRepository.getAdminVasya()}
        };
    }

    @DataProvider
    public Object[][] lockWrongUser() {
        return new Object[][]{
                {UserRepository.getAdmin(), UserRepository.getUserWithWrongPassword()}
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

    @Test(dataProvider = "lockWrongUser", priority = 2)
    public void lockUserByUnsuccessfulLogin(User admin, User UserWithWrongPassword) {
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService();
        LoginService loginService = new LoginService()
                .unsuccessfulUserLogin(UserWithWrongPassword)
                .unsuccessfulUserLogin(UserWithWrongPassword)
                .unsuccessfulUserLogin(UserWithWrongPassword);

        Assert.assertTrue(adminService.isUserLocked(UserWithWrongPassword));
    }

    @Test(dataProvider = "lockUser", priority = 3)
    public void unlockUserFromAdmin(User admin, User someUser) {
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .unlockUser(someUser);

        Assert.assertFalse(adminService.isUserLocked(someUser));
    }

    @Test(dataProvider = "lockAdminVasya", priority = 4)
    public void lockAdminFromAdmin(User adminVasya) {
        LockService adminService = new LoginService()
                .successfulAdminLogin(adminVasya)
                .gotoLockService()
                .lockUser(adminVasya);

        Assert.assertTrue(adminService.isUserLocked(adminVasya));
    }

    @Test(dataProvider = "Admin", priority = 9)
    public void unlockAll(User admin) {
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .unlockAllUsers();

        Assert.assertEquals(adminService.getAllLockedUsers(), "");
    }
}

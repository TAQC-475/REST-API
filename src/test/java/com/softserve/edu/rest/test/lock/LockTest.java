package com.softserve.edu.rest.test.lock;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.data.dataproviders.LockData;
import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.services.LockService;
import com.softserve.edu.rest.services.LoginService;
import io.qameta.allure.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LockTest {

    public static final Logger logger = LoggerFactory.getLogger(LockService.class);

    @BeforeClass
    public void createExtraUsers() {
        logger.info("BEFORE CLASS reset service to initial state");
        new GuestService().resetServiceToInitialState();
        new LoginService()
                .successfulAdminLogin(UserRepository.getAdmin())
                .gotoManageUserService()
                .createUser(UserRepository.getUserDana())
                .gotoManageUserService()
                .createUser(UserRepository.getAdminVasya());
        logger.info("END OF BEFORE CLASS method");
    }

    @Description("Verify that logged-in admin can  lock user")
    @Parameters({"Admin login", "User for locking"})
    @Test(priority = 1, dataProvider = "lockUser", dataProviderClass = LockData.class)
    public void lockUserFromAdmin(User admin, User simpleUser) {
        logger.info("START TEST Lock user = {}, as admin = {} ", simpleUser.getName(), admin.getName());
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .lockUser(simpleUser);

        Assert.assertTrue(adminService.isUserLocked(simpleUser));
        logger.info("END OF THE TEST");
    }

    @Description("Verify that user will be locked after three unsuccessful tries of login")
    @Parameters({"Admin login", "User with wrong password"})
    @Test(priority = 2, dataProvider = "lockWrongUser", dataProviderClass = LockData.class)
    public void lockUserByUnsuccessfulLogin(User admin, User userWithWrongPassword) {
        logger.info("START TEST Lock user by tree unsuccessful login tries = {}, as admin = {} ", userWithWrongPassword.getName(), admin.getName());
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService();
        LoginService loginService = new LoginService()
                .unsuccessfulUserLogin(userWithWrongPassword)
                .unsuccessfulUserLogin(userWithWrongPassword)
                .unsuccessfulUserLogin(userWithWrongPassword);

        Assert.assertTrue(adminService.isUserLocked(userWithWrongPassword));
        logger.info("END OF THE TEST");
    }

    @Description("Verify that logged-in admin can unlock user")
    @Parameters({"Admin login", "User for unlocking"})
    @Test(priority = 3, dataProvider = "lockUser", dataProviderClass = LockData.class)
    public void unlockUserFromAdmin(User admin, User someUser) {
        logger.info("START TEST Unlock user = {}, as admin = {} ", someUser.getName(), admin.getName());
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .unlockUser(someUser);

        Assert.assertFalse(adminService.isUserLocked(someUser));
        logger.info("END OF THE TEST");
    }

    @Description("Verify that logged-in admin can lock himself")
    @Parameters({"Admin for locking himself"})
    @Test(priority = 4, dataProvider = "lockAdminVasya", dataProviderClass = LockData.class)
    public void lockAdminFromAdmin(User adminVasya) {
        logger.info("START TEST Lock admin = {} by himself ", adminVasya.getName());
        LockService adminService = new LoginService()
                .successfulAdminLogin(adminVasya)
                .gotoLockService()
                .lockUser(adminVasya);

        Assert.assertTrue(adminService.isUserLocked(adminVasya));
        logger.info("END OF THE TEST");
    }

    @Description("Verify that unlock all users and admins work")
    @Parameters({"Admin login"})
    @Test(priority = 9, dataProvider = "Admin", dataProviderClass = LockData.class)
    public void unlockAll(User admin) {
        logger.info("START TEST Unlock all as admin = {} ", admin.getName());
        LockService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .unlockAllUsers();

        Assert.assertEquals(adminService.getAllLockedUsers(), "");
        logger.info("END OF THE TEST");
    }
}
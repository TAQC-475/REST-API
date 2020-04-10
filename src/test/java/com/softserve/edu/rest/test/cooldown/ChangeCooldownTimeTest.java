package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChangeCooldownTimeTest {

    public static final Logger logger = LoggerFactory.getLogger(ChangeCooldownTimeTest.class);

    private CooldownService cooldownService;
    private AdministrationService adminService;

    @BeforeSuite
    public void createExtraUsers() {
        logger.info("BEFORE CLASS reset service to initial state");
        new GuestService().resetServiceToInitialState();
    }

    @DataProvider
    public Object[][] defaultCoolTime() {
        return new Object[][]{
                {LifetimeRepository.getDefaultCooldownTime()}
        };
    }

    @DataProvider
    public Object[][] changeCooldownTimePositive() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getNewCooldownTime()},
        };
    }

    @Test(dataProvider = "defaultCoolTime", priority = 1)
    public void checkCooldownTime(Lifetime defaultTime) {
        logger.info("START TEST Check cooldown time = {} ", defaultTime.getTimeAsText());
        cooldownService = new CooldownService();
        Assert.assertEquals(cooldownService
                .getCooldownTime()
                .getTime(), defaultTime.getTime());
        logger.info("END OF THE TEST");
    }

    @Test(dataProvider = "changeCooldownTimePositive", priority = 2)
    public void cooldownTimeChangeTest(User admin, Lifetime newLifeTime) {
        logger.info("START TEST Change cooldown positive time = {}, as admin = {} ", newLifeTime.getTimeAsText(), admin.getName());
        Lifetime checkNewCooldownTime = new LoginService()
                .successfulAdminLogin(admin)
                .gotoCooldownService()
                .changeCooldown(newLifeTime)
                .getCooldownTime();

        Assert.assertEquals(checkNewCooldownTime.getTime(),
                newLifeTime.getTime());
        logger.info("END OF THE TEST");
    }

}

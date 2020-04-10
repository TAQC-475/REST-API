package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.CooldownService;
import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.tools.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChangeCooldownTimeNegativeTest {

    public static final Logger logger = LoggerFactory.getLogger(ChangeCooldownTimeTest.class);

    private CooldownService cooldownService;

    @BeforeSuite
    public void createExtraUsers() {
        logger.info("BEFORE CLASS reset service to initial state");
        new GuestService().resetServiceToInitialState();
    }

    @DataProvider
    public Object[][] simpleUser() {
        return new Object[][]{
                {UserRepository.getValidUser(), LifetimeRepository.getNewCooldownTime()}
        };
    }

    @DataProvider
    public Object[][] negativeTime() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getNegativeLifeTime(),
                        LifetimeRepository.getDefaultCooldownTime()}
        };
    }

    @Test(priority = 1, dataProvider = "simpleUser")
    public void changeCooldownTimeAsUser(User simpleUser, Lifetime newLifeTime) {
        logger.info("START TEST Change cooldown time to = {}, user = {}", newLifeTime.getTime(), simpleUser.getName());
        SimpleEntity response = new LoginService()
                .successfulAdminLogin(simpleUser)
                .gotoCooldownService()
                .changeCooldown(newLifeTime)
                .getResponse();

        Assert.assertTrue(EntityUtils.isUserActionUnSuccessful(response));
        logger.info("END OF THE TEST");
    }

    @Test(priority = 2, dataProvider = "negativeTime")
    public void cooldownTimeWithNegativeNumberTest(User admin, Lifetime newLifeTime, Lifetime defaultTime) {
        logger.info("START TEST Change cooldown time with negative number = {}, as admin = {}", newLifeTime.getTime(), admin.getName());
        Lifetime checkNewCooldownTime = new LoginService()
                .successfulAdminLogin(admin)
                .gotoCooldownService()
                .changeCooldown(newLifeTime)
                .getCooldownTime();

        Assert.assertNotEquals(checkNewCooldownTime.getTime(),
                defaultTime.getTime());
        logger.info("END OF THE TEST");
    }

}

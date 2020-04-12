package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.CooldownData;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.CooldownService;
import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ChangeCooldownTimeNegativeTest {

    public static final Logger logger = LoggerFactory.getLogger(ChangeCooldownTimeTest.class);

    private CooldownService cooldownService;

    @BeforeSuite
    public void createExtraUsers() {
        logger.info("BEFORE CLASS reset service to initial state");
        new GuestService().resetServiceToInitialState();
    }

    @Description("Verify that not admin user can't change cooldown time")
    @Parameters({"User login", "New life time"})
    @Test(priority = 1, dataProvider = "simpleUser", dataProviderClass = CooldownData.class)
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

    @Description("Verify that cooldown time can't be set as negative time")
    @Parameters({"Admin login", "New negative lifetime to put", "Default time to compare"})
    @Test(priority = 2, dataProvider = "negativeTime", dataProviderClass = CooldownData.class)
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

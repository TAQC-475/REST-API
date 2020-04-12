package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.CooldownData;
import com.softserve.edu.rest.services.*;
import io.qameta.allure.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ChangeCooldownTimeTest {

    public static final Logger logger = LoggerFactory.getLogger(ChangeCooldownTimeTest.class);

    @BeforeSuite
    public void createExtraUsers() {
        logger.info("BEFORE CLASS reset service to initial state");
        new GuestService().resetServiceToInitialState();
    }

    @Description("Verify that GET cooldown time request returns default time")
    @Parameters({"Get current lifetime"})
    @Test(priority = 1, dataProvider = "defaultCoolTime", dataProviderClass = CooldownData.class)
    public void checkCooldownTime(Lifetime defaultTime) {
        logger.info("START TEST Check cooldown time = {} ", defaultTime.getTimeAsText());
        CooldownService cooldownService = new CooldownService();
        Assert.assertEquals(cooldownService
                .getCooldownTime()
                .getTime(), defaultTime.getTime());
        logger.info("END OF THE TEST");
    }

    @Description("Verify that cooldown time can be changed on other positive time")
    @Parameters({"Admin login", "Set new lifetime"})
    @Test(priority = 2, dataProvider = "changeCooldownTimePositive", dataProviderClass = CooldownData.class)
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

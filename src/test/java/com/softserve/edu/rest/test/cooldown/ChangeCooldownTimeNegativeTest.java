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
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChangeCooldownTimeNegativeTest {

    private CooldownService cooldownService;

    @BeforeSuite
    public void createExtraUsers() {
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
    public void changeCoolDownTimeAsUser(User simpleUser, Lifetime newLifeTime) {
        SimpleEntity response = new LoginService()
                .successfulAdminLogin(simpleUser)
                .gotoCooldownService()
                .changeCooldown(newLifeTime)
                .getResponse();

        Assert.assertTrue(EntityUtils.isUserActionUnSuccessful(response));
    }

    @Test(priority = 2, dataProvider = "negativeTime")
    public void cooldownTimeWithNegativeNumberTest(User admin, Lifetime newLifeTime, Lifetime defaultTime) {
        Lifetime checkNewCooldownTime = new LoginService()
                .successfulAdminLogin(admin)
                .gotoCooldownService()
                .changeCooldown(newLifeTime)
                .getCooldownTime();

        Assert.assertEquals(checkNewCooldownTime.getTime(),
                defaultTime.getTime());
    }

}

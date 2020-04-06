package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.CooldownService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChangeCooldownTimeNegativeTest {

    private CooldownService cooldownService;

    @DataProvider
    public Object[][] notExistingData() {
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

    @Test(dataProvider = "notExistingData", expectedExceptions = RuntimeException.class)
    public void changeCoolDownTimeAsUser(User notExistingUser, Lifetime newLifeTime) {
        Lifetime checkNewCooldownTime = new LoginService()
                .successfulAdminLogin(notExistingUser)
                .gotoCooldownService()
                .changeCooldown(newLifeTime);
         // todo it the right way
        Assert.assertEquals(checkNewCooldownTime.getTime(), newLifeTime.getTime());
    }

    @Test(dataProvider = "negativeTime", expectedExceptions = RuntimeException.class)
    public void cooldownTimeWithNegativeNumberTest(User admin, Lifetime newLifeTime, Lifetime defaultTime) {
        Lifetime checkNewCooldownTime = new LoginService()
                .successfulAdminLogin(admin)
                .gotoCooldownService()
                .changeCooldown(newLifeTime);
        Assert.assertEquals(checkNewCooldownTime
                .getTime(), defaultTime.getTime());
    }

}

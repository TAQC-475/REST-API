package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.CooldownService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UserService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChangeCooldownTimeNegativeTest {

    @DataProvider
    public Object[][] notExistingData() {
        return new Object[][]{
                {UserRepository.notExistingUser(), LifetimeRepository.getNewCooldownTime()}
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
        System.out.println("*** "+checkNewCooldownTime.getTime());   // todo it the right way
        Assert.assertEquals(checkNewCooldownTime.getTime(), newLifeTime.getTime());
    }

    @Test(dataProvider = "negativeTime", expectedExceptions = RuntimeException.class)
    public void cooldownTimeWithNegativeNumberTest(User admin, Lifetime lifetime, Lifetime defaultTime) {
        LoginService loginService = new LoginService();
        Lifetime checkNewCooldownTime = new LoginService()
                .successfulAdminLogin(admin)
                .gotoCooldownService()
                .changeCooldown(lifetime);
        Assert.assertEquals(checkNewCooldownTime.getTime(), defaultTime.getTime());
    }

}

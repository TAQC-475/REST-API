package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.AdministrationService;
import com.softserve.edu.rest.services.CooldownService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChangeCooldownTimeTest {

    private CooldownService cooldownService;
    private AdministrationService adminService;

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
        cooldownService = new CooldownService();
        Assert.assertEquals(cooldownService
                .getCooldownTime()
                .getTimeAsText(), defaultTime.getTimeAsText());
    }

    @Test(dataProvider = "changeCooldownTimePositive", priority = 2)
    public void cooldownTimeChangeTest(User admin, Lifetime newLifeTime) {
        Lifetime checkNewCooldownTime = new LoginService()
                .successfulAdminLogin(admin)
                .gotoCooldownService()
                .changeCooldown(newLifeTime);

        Assert.assertEquals(checkNewCooldownTime.getTimeAsText(),
                newLifeTime.getTimeAsText());
    }

    @AfterClass
    public void setCooldownTimeForDefault() {
         new LoginService()
                .successfulAdminLogin(UserRepository.getAdmin())
                .gotoCooldownService()
                .changeCooldown(LifetimeRepository.getDefaultCooldownTime());
    }

}

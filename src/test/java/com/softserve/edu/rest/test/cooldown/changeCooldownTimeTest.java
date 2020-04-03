package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.AdministrationService;
import com.softserve.edu.rest.services.CooldownService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class changeCooldownTimeTest {

    private CooldownService cooldownService;
    private AdministrationService adminService;

    @DataProvider
    public Object[][] defaultCoolTime() {
        return new Object[][]{
                {LifetimeRepository.getDefaultCooldownTime()}
        };
    }

    @DataProvider
    public Object[][] correctUser() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getDefaultCooldownTime(),
                        LifetimeRepository.getNewCooldownTime()},
        };
    }

    @Test(dataProvider = "defaultCoolTime")
    public void checkCooldownTime(Lifetime defaultTime) {
        cooldownService = new CooldownService();
        Assert.assertEquals(cooldownService
                .getCooldownTime()
                .getTimeAsText(), defaultTime.getTimeAsText());
    }

    @Test(dataProvider = "correctUser")
    public void coolDownTimeChangeTest(User admin, Lifetime defaultTime, Lifetime lifetime) {
        Lifetime checkNewCooldownTime = new LoginService()
                .successfulAdminLogin(admin)
                .gotoCooldownService()
                .changeCooldown(lifetime);

        Assert.assertEquals(checkNewCooldownTime.getTimeAsText(),
                lifetime.getTimeAsText());
    }

}

//    @Test(dataProvider = "correctUser")
//    public void coolDownTimeChangeTest(User admin, Lifetime defaultTime, Lifetime lifetime) {
//        Lifetime checkNewCooldownTime = new LoginService()
//                .successfulAdminLogin(admin)
//                .gotoCooldownService()
//                .changeCooldown(lifetime);
// returns boolean if time was changed
//        Assert.assertEquals(cooldownService.toString(), "750000");//defaultTime.getTime());
//    }

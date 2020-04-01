package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.CooldownService;
import com.softserve.edu.rest.services.GuestService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class changeCooldownTimeTest {

    private GuestService guestService;

    @DataProvider
    public Object[][] user() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getDefaultCooldownTime()}
        };
    }

    @Test(dataProvider = "user")
    public void checkCooldownTime(User admin, Lifetime defaultTime) {
        guestService = new GuestService();
        Assert.assertEquals(guestService
                .successfulUserLogin(admin)
                .getCooldownTime()
                .getTimeAsText(), defaultTime.getTimeAsText());
    }

}

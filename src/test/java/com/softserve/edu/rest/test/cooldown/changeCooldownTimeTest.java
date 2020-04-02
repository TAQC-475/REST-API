package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.services.GuestServiceDoNotUse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class changeCooldownTimeTest {

    private GuestServiceDoNotUse guestService;

    @DataProvider
    public Object[][] defaultCoolTime() {
        return new Object[][]{
                {LifetimeRepository.getDefaultCooldownTime()}
        };
    }

    @Test(dataProvider = "defaultCoolTime")
    public void checkCooldownTime(Lifetime defaultTime) {
        guestService = new GuestServiceDoNotUse();
        Assert.assertEquals(guestService
                .getCooldownTime()
                .getTimeAsText(), defaultTime.getTimeAsText());
    }

}

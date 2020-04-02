package com.softserve.edu.rest.test.cooldown;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.services.CooldownService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class changeCooldownTimeTest {

    private CooldownService cooldownService;

    @DataProvider
    public Object[][] defaultCoolTime() {
        return new Object[][]{
                {LifetimeRepository.getDefaultCooldownTime()}
        };
    }

    @Test(dataProvider = "defaultCoolTime")
    public void checkCooldownTime(Lifetime defaultTime) {
        cooldownService = new CooldownService();
        Assert.assertEquals(cooldownService
                .getCooldownTime()
                .getTimeAsText(), defaultTime.getTimeAsText());
    }

}

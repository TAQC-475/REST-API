package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TokenLifetimeTest extends RestTestRunner {

    @DataProvider
    public Object[][] correctAdminExtend() {
        return new Object[][]{
                { UserRepository.getAdmin(), LifetimeRepository.getExtend()}
        };
    }

    @DataProvider
    public Object[][] correctAdminShort() {
        return new Object[][]{
                { UserRepository.getAdmin(), LifetimeRepository.getShort()}
        };
    }

    @Test(dataProvider = "correctAdminExtend", priority = 1)
    public void verifyTokenChange(User admin, Lifetime extend){
        AdministrationService administrationService =new LoginService().successfulAdminLogin(admin);
        TokensService tokensService = new TokensService(administrationService.getLoginedUser());

        tokensService.changeCurrentLifetime(extend);

        Assert.assertEquals(tokensService.getCurrentLifetime(), extend);

    }

    @Test(dataProvider = "correctAdminShort", priority = 2)
    public void verifyTokenLifetime(User admin, Lifetime tokenShort){

        AdministrationService administrationService=new LoginService().successfulAdminLogin(admin);
        TokensService tokensService=new TokensService(administrationService.getLoginedUser());
        tokensService.changeCurrentLifetime(tokenShort).waitTokenLifeTime(tokenShort);

        //Assert.assertFalse(administrationService.isUserLogged(admin));

    }
}

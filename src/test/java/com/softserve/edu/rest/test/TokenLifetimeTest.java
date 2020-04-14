package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

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

    @DataProvider
    public Object[][] correctAdminNegative() {
        return new Object[][]{
                { UserRepository.getAdmin(), LifetimeRepository.getNegativeLifeTime()}
        };
    }

    @DataProvider
    public Object[][] correctAdminZero() {
        return new Object[][]{
                { UserRepository.getAdmin(), LifetimeRepository.getZeroLifetime()}
        };
    }

    @Test(dataProvider = "correctAdminExtend", priority = 1)
    public void verifyTokenChange(User admin, Lifetime tokenExtend){
        AdministrationService administrationService =new LoginService().successfulAdminLogin(admin);
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService.changeCurrentLifetime(tokenExtend);

        Assert.assertEquals(tokensService.getCurrentLifetime(), tokenExtend);

    }

    @Test(dataProvider = "correctAdminShort", priority = 2)
    public void verifyTokenLifetime(User admin, Lifetime tokenShort){

        AdministrationService administrationService=new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(tokenShort)
                .waitTokenLifeTime(tokenShort);

        List<User> admins = usersService.getAdmins();

        Assert.assertTrue(admins.isEmpty());
    }

    @Test(dataProvider = "correctAdminNegative", priority = 3)
    public void verifyNegativeTokenLifetime(User admin, Lifetime negativeLifeTime){

        AdministrationService administrationService=new LoginService().successfulAdminLogin(admin);
        TokensService tokensService=new TokensService(administrationService.getLogginedUser());

        tokensService.changeCurrentLifetime(negativeLifeTime);

////////тут
        //Assert.assertEquals(tokensService.getCurrentLifetime(), tokenShort);
    }

    @Test(dataProvider = "correctAdminZero", priority = 4)
    public void verifyZeroTokenLifetime(User admin, Lifetime zeroLifeTime){

        AdministrationService administrationService=new LoginService().successfulAdminLogin(admin);
        TokensService tokensService=new TokensService(administrationService.getLogginedUser());

        tokensService.changeCurrentLifetime(zeroLifeTime);

////////тут
        //Assert.assertEquals(tokensService.getCurrentLifetime(), tokenShort);
    }
}

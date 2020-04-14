package com.softserve.edu.rest.test.token;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.services.*;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TokenLifetimeTest extends RestTestRunner {


    @Test(dataProvider = "correctAdminExtend", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void verifyTokenChange(User admin, Lifetime tokenExtend) {
        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(tokenExtend);

        Assert.assertEquals(tokensService.getCurrentLifetime(), tokenExtend);

    }

    @Test(dataProvider = "correctAdminShort", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void verifyTokenLifetime(User admin, Lifetime tokenShort) {

        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(tokenShort)
                .waitTokenLifeTime(tokenShort);

        Assert.assertTrue(usersService.getAdmins().isEmpty());
    }

    @Test(dataProvider = "correctAdminNegative", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void verifyNegativeTokenLifetime(User admin, Lifetime negativeLifeTime) {

        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(negativeLifeTime);

        Assert.assertTrue(usersService.getAdmins().isEmpty());
    }

    @Test(dataProvider = "correctAdminZero", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void verifyZeroTokenLifetime(User admin, Lifetime zeroLifeTime) {

        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(zeroLifeTime);

        Assert.assertTrue(usersService.getAdmins().isEmpty());
    }

    @Test(dataProvider = "correctAdminUpdate", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void updatingTokenLifeTime(User admin, Lifetime tokenShort, Lifetime tokenExtend)
    {
        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(tokenShort)
                .waitTokenLifeTime(tokenShort);
//                .updateLifetime();
//        Assert.assertTrue(usersService.getAdmins().isEmpty());
//      tokensService.updateCurrentLifetime();
//        Assert.assertFalse(usersService.getAdmins().isEmpty());

    }
}

package com.softserve.edu.rest.test.token;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.services.*;
import com.softserve.edu.rest.test.RestTestRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertThrows;


public class TokenLifetimeTest extends RestTestRunner {

    public static final Logger logger = LoggerFactory.getLogger(TokenLifetimeTest.class);

    @Test(dataProvider = "correctAdminExtend", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void verifyTokenChange(User admin, Lifetime tokenExtend) {
        logger.info("verifyTokenChange START, admin = " + admin.toString());

        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(tokenExtend);

        Assert.assertEquals(tokensService.getCurrentLifetime(), tokenExtend);

        logger.info("verifyTokenChange DONE, admin = " + admin);
    }

    @Test(dataProvider = "correctAdminShort", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void verifyTokenLifetime(User admin, Lifetime tokenShort) {
        logger.info("verifyTokenLifetime START, admin = " + admin.toString());

        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(tokenShort)
                .waitTokenLifeTime(tokenShort);

        Assert.assertTrue(usersService.getAdmins().isEmpty());

        logger.info("verifyTokenChange DONE, admin = " + admin);
    }

    @Test(dataProvider = "correctAdminNegative", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void verifyNegativeTokenLifetime(User admin, Lifetime negativeLifeTime) {
        logger.info("verifyTokenLifetime START, admin = " + admin.toString());

        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(negativeLifeTime);

        Assert.assertTrue(usersService.getAdmins().isEmpty());

        logger.info("verifyTokenChange DONE, admin = " + admin);
    }

    @Test(dataProvider = "correctAdminZero", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void verifyZeroTokenLifetime(User admin, Lifetime zeroLifeTime) {
        logger.info("verifyTokenLifetime START, admin = " + admin.toString());

        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(zeroLifeTime);

        Assert.assertTrue(usersService.getAdmins().isEmpty());

        logger.info("verifyTokenChange DONE, admin = " + admin);
    }

    @Test(dataProvider = "correctAdminUpdate", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void changingTokenLifetimeAfterExpirationThrows(User admin, Lifetime tokenShort, Lifetime tokenExtend)
    {
        logger.info("verifyTokenLifetime START, admin = " + admin.toString());

        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        assertThrows(RuntimeException.class, () -> {
        tokensService
                .changeCurrentLifetime(tokenShort) // 5
                .waitTokenLifeTime(tokenShort) // 0
                .changeCurrentLifetime(tokenExtend); // 900000
        });

        logger.info("verifyTokenChange DONE, admin = " + admin);
    }

    @Test(dataProvider = "correctAdminMax", dataProviderClass =TokenLifetimeData.class,
            description = " ")
    public void changingToIncorrectTokenLifetime(User admin, Lifetime maxLifeTime)
    {
        logger.info("verifyTokenLifetime START, admin = " + admin.toString());

        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        assertThrows(RuntimeException.class, () -> {
            tokensService
                    .changeCurrentLifetime(maxLifeTime.toString());
        });

        logger.info("verifyTokenChange DONE, admin = " + admin);
    }
}

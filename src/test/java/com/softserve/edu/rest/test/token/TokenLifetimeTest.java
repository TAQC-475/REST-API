package com.softserve.edu.rest.test.token;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.services.*;
import com.softserve.edu.rest.test.RestTestRunner;
import io.qameta.allure.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertThrows;


public class TokenLifetimeTest extends RestTestRunner {

    public static final Logger logger = LoggerFactory.getLogger(TokenLifetimeTest.class);

    /**
     * set lifetime with positive values
     * do PUT request to set token lifetime
     *
     * @param admin       for login
     * @param tokenExtend time for setting
     */
    @Description("Verify that token lifetime can be changed on bigger than default.")
    @Parameters({"Admin login", "Extend lifetime"})
    @Test(dataProvider = "extendTokenLifeTimeDataProvider", dataProviderClass = TokenLifetimeData.class)
    public void verifyTokenChange(User admin, Lifetime tokenExtend) {
        logger.info("verifyTokenChange START, admin = {}", admin.toString());
        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(tokenExtend);

        Assert.assertEquals(tokensService.getCurrentLifetime(), tokenExtend);
        logger.info("verifyTokenChange DONE, token lifetime = {}", tokensService.getCurrentLifetime());
    }

    /**
     * set lifetime with positive values
     * do PUT request to set token lifetime
     * do GET request to get all admins
     *
     * @param admin       for login
     * @param tokenShort time for setting
     */
    @Description("Verify that admin is unlogged after token has expired.")
    @Parameters({"Admin login", "Short lifetime"})
    @Test(dataProvider = "shortTokenLifeTimeDataProvider", dataProviderClass = TokenLifetimeData.class)
    public void verifyTokenLifetime(User admin, Lifetime tokenShort) {
        logger.info("verifyTokenLifetime START, admin = " + admin.toString());
        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(tokenShort)
                .waitTokenLifeTime(tokenShort);

        Assert.assertTrue(usersService.getAdmins().isEmpty());
        logger.info("verifyTokenLifetime DONE.");
    }

    /**
     * set lifetime with negative values
     * do PUT request to set token lifetime
     *
     * @param admin       for login
     * @param negativeLifeTime time for setting
     */
    @Description("Verify that admin is unlogged after changing token lifetime to negative.")
    @Parameters({"Admin login", "Negative lifetime"})
    @Test(dataProvider = "negativeTokenLifeTimeDataProvider", dataProviderClass = TokenLifetimeData.class)
    public void verifyNegativeTokenLifetime(User admin, Lifetime negativeLifeTime) {
        logger.info("verifyNegativeTokenLifetime START, admin = " + admin.toString());
        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        UsersService usersService = new UsersService(administrationService.getLogginedUser());
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        tokensService
                .changeCurrentLifetime(negativeLifeTime);

        Assert.assertTrue(usersService.getAdmins().isEmpty());
        logger.info("verifyNegativeTokenLifetime DONE.");
    }

    /**
     * set lifetime with zero values
     * do PUT request to set token lifetime
     *
     * @param admin       for login
     * @param zeroLifeTime time for setting
     */
    @Description("Verify that admin is unlogged after changing token lifetime to zero.")
    @Parameters({"Admin login", "Zero lifetime"})
    @Test(dataProvider = "zeroTokenLifeTimeDataProvider", dataProviderClass = TokenLifetimeData.class)
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

    /**
     * set lifetime with positive values
     * do PUT request to set token lifetime
     * wait token lifetime
     * do PUT request to set token lifetime
     *
     * @param admin       for login
     * @param tokenShort time for setting
     * @param tokenExtend time for setting
     */
    @Description("Verify that admin can't change token lifetime after expiration.")
    @Parameters({"Admin login", "New lifetime"})
    @Test(dataProvider = "tokenLifeTimeDataProvider", dataProviderClass = TokenLifetimeData.class)
    public void changingTokenLifetimeAfterExpirationThrows(User admin, Lifetime tokenShort, Lifetime tokenExtend) {
        logger.info("changingTokenLifetimeAfterExpirationThrows START, admin = " + admin.toString());
        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        assertThrows(RuntimeException.class, () -> {
            tokensService
                    .changeCurrentLifetime(tokenShort)
                    .waitTokenLifeTime(tokenShort)
                    .changeCurrentLifetime(tokenExtend);
        });
        logger.info("changingTokenLifetimeAfterExpirationThrows DONE.");
    }

    /**
     * set lifetime with positive values
     * do PUT request to set token lifetime
     *
     * @param admin       for login
     * @param maxLifeTime time for setting
     */
    @Description("Verify that token lifetime  has bound.")
    @Parameters({"Admin login", "Maximal lifetime"})
    @Test(dataProvider = "maximalTokenLifeTimeDataProvider", dataProviderClass = TokenLifetimeData.class)
    public void changingToIncorrectTokenLifetime(User admin, String maxLifeTime) {
        logger.info("changingToIncorrectTokenLifetime START, admin = " + admin.toString());
        AdministrationService administrationService = new LoginService().successfulAdminLogin(admin);
        TokensService tokensService = new TokensService(administrationService.getLogginedUser());

        assertThrows(RuntimeException.class, () -> {
            tokensService
                    .changeCurrentLifetime(maxLifeTime);
        });
        logger.info("changingToIncorrectTokenLifetime DONE.");
    }
}

package com.softserve.edu.rest.test.token;

import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

public class TokenLifetimeData {

    public static final Logger logger = LoggerFactory.getLogger(TokenLifetimeData.class);

    @DataProvider
    public Object[][] correctAdminExtend() {
        logger.info("@DataProvider correctAdminExtend() DONE");
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getExtend()}
        };
    }

    @DataProvider
    public Object[][] correctAdminShort() {
        logger.info("@DataProvider correctAdminShort() DONE");
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getShort()}
        };
    }

    @DataProvider
    public Object[][] correctAdminNegative() {
        logger.info("@DataProvider correctAdminNegative() DONE");
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getNegativeLifeTime()}
        };
    }

    @DataProvider
    public Object[][] correctAdminZero() {
        logger.info("@DataProvider correctAdminZero() DONE");
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getZeroLifetime()}
        };
    }

    @DataProvider
    public Object[][] correctAdminUpdate() {
        logger.info("@DataProvider correctAdminUpdate() DONE");
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getShort(), LifetimeRepository.getExtend()}
        };
    }

    @DataProvider
    public Object[][] correctAdminMax() {
        logger.info("@DataProvider correctAdminUpdate() DONE");
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getLifeTimeMoreThanMax()}
        };
    }
}

package com.softserve.edu.rest.test.token;

import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class TokenLifetimeData {

    @DataProvider
    public Object[][] correctAdminExtend() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getExtend()}
        };
    }

    @DataProvider
    public Object[][] correctAdminShort() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getShort()}
        };
    }

    @DataProvider
    public Object[][] correctAdminNegative() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getNegativeLifeTime()}
        };
    }

    @DataProvider
    public Object[][] correctAdminZero() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getZeroLifetime()}
        };
    }

    @DataProvider
    public Object[][] correctAdminUpdate() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getShort(),LifetimeRepository.getExtend()}
        };
    }}

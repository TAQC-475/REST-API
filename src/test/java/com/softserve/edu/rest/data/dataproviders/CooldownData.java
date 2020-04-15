package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class CooldownData {

    @DataProvider
    public Object[][] simpleUser() {
        return new Object[][]{
                {UserRepository.getValidUser(), LifetimeRepository.getNewCooldownTime()}
        };
    }

    @DataProvider
    public Object[][] negativeTime() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getCooldownNegative(), LifetimeRepository.getDefaultCooldownTime()},
                {UserRepository.getAdmin(), LifetimeRepository.getCooldownMoreThanMax(), LifetimeRepository.getDefaultCooldownTime()}
        }

                ;
    }

    @DataProvider
    public Object[][] defaultCoolTime() {
        return new Object[][]{
                {LifetimeRepository.getDefaultCooldownTime()}
        };
    }

    @DataProvider
    public Object[][] changeCooldownTimePositive() {
        return new Object[][]{
                {UserRepository.getAdmin(), LifetimeRepository.getZeroLifetime()},
                {UserRepository.getAdmin(), LifetimeRepository.getCooldownMin()},
                {UserRepository.getAdmin(), LifetimeRepository.getNewCooldownTime()},
                {UserRepository.getAdmin(), LifetimeRepository.getCooldownMax()}};
}

}

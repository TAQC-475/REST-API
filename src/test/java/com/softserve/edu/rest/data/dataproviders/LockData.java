package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class LockData {

    @DataProvider
    public Object[][] lockUser() {
        return new Object[][]{
                {UserRepository.getAdmin(), UserRepository.getUserDana()}
        };
    }

    @DataProvider
    public Object[][] Admin() {
        return new Object[][]{
                {UserRepository.getAdmin()}
        };
    }

    @DataProvider
    public Object[][] lockAdminVasya() {
        return new Object[][]{
                {UserRepository.getAdminVasya()}
        };
    }

    @DataProvider
    public Object[][] lockWrongUser() {
        return new Object[][]{
                {UserRepository.getAdmin(), UserRepository.getUserWithWrongPassword()}
        };
    }

}

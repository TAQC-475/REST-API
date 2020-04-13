package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class AdminData {

    @DataProvider
    public Object[][] createAdminTestData() {
        return new Object[][]{{UserRepository.getAdmin()
            , UserRepository.getAdminVasya()
            , UserRepository.getUserVasya()
            , UserRepository.getNonExistingUser()}};
    }

    @DataProvider(parallel = true)
    public Object[][] removeAdminsTestData() {
        return new Object[][]{{UserRepository.getAdmin(),UserRepository.getAdminVasya(),UserRepository.getAdminDana()}};
    }

    @DataProvider
    public Object[][] removeAdminTestData(){
        return new Object[][]{{UserRepository.getAdmin(),UserRepository.getAdminVasya()}};
    }

    @DataProvider
    public Object[][] removeUserTestData(){
        return new Object[][]{{UserRepository.getAdmin(), UserRepository.getNonExistingUser()}};
    }
}

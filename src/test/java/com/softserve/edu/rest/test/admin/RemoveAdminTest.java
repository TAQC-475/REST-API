package com.softserve.edu.rest.test.admin;

import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveAdminTest {

    @Test
    public void removeAdminTest() {
        boolean actual = new LoginService()
            .successfulAdminLogin(UserRepository.getAdmin())
            .gotoManageUserService()
            .removeUserAndCheckIt(UserRepository.getAdmin());

        Assert.assertTrue(actual);
    }

}

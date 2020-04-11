package com.softserve.edu.rest.test.admin;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.data.dataproviders.AdminData;
import com.softserve.edu.rest.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RemoveAdminsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveAdminTest.class);

    @BeforeClass
    public void createNewAdmin(){
        new LoginService()
            .successfulAdminLogin(UserRepository.getAdmin())
            .gotoManageUserService()
            .createUser(UserRepository.getAdminDana())
            .goToLoginService()
            .successfulLogout(ApplicationState.get().getLastLoggined());
    }



    @Test(dataProvider = "removeAdminsTestData", dataProviderClass = AdminData.class)
    public void removeFirstAdmin(User firstAdmin, User secondAdmin, User adminDana) {
        boolean actual = new LoginService()
            .successfulAdminLogin(firstAdmin)
            .gotoManageUserService()
            .removeUser(secondAdmin)
            .goToLoginService()
            .successfulAdminLogin(adminDana)
            .gotoUsersService()
            .isUserPresent(secondAdmin);

        Assert.assertFalse(actual);
    }

    @Test(dataProvider = "removeAdminsTestData", dataProviderClass = AdminData.class)
    public void removeSecondAdmin(User firstAdmin, User secondAdmin, User adminDana){
        boolean actual = new LoginService()
            .successfulAdminLogin(secondAdmin)
            .gotoManageUserService()
            .removeUser(firstAdmin)
            .goToLoginService()
            .successfulAdminLogin(adminDana)
            .gotoUsersService()
            .isUserPresent(firstAdmin);

        Assert.assertFalse(actual);
    }
}

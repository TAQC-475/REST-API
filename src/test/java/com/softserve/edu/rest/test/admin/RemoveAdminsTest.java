package com.softserve.edu.rest.test.admin;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.AdministrationService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.tools.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RemoveAdminsTest {

    @DataProvider(parallel = true)
    public Object[][] getAdmins() {
        return new Object[][]{{UserRepository.getAdmin(),UserRepository.getAdminVasya()}};
    }

    @Test(dataProvider = "getAdmins")
    public void removeFirstAdmin(User firstAdmin, User secondAdmin) {
        AdministrationService actual = new LoginService()
            .successfulAdminLogin(firstAdmin)
            .gotoManageUserService()
            .removeUser(secondAdmin);
    }

    @Test(dataProvider = "getAdmins")
    public void removeSecondAdmin(User firstAdmin, User secondAdmin){
        AdministrationService actual = new LoginService()
            .successfulAdminLogin(secondAdmin)
            .gotoManageUserService()
            .removeUser(firstAdmin);
    }
}

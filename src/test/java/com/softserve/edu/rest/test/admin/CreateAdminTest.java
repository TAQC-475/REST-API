package com.softserve.edu.rest.test.admin;

import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.services.LoginService;
import org.testng.annotations.Test;

public class CreateAdminTest {
    @Test
    public void adminTest(){
        new LoginService()
            .successfulAdminLogin(UserRepository.getAdmin())
            .gotoManageUserService()
            .createUser(UserRepository.getAdminVasya())
            .gotoManageUserService()
            .removeUser(UserRepository.getAdminVasya())
            .gotoManageUserService()
            .createUser(UserRepository.getUserVasya())
            .goToLoginService();
//            .successfulLogout()
    }

}

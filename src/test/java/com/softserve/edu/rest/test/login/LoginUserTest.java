package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.AdministrationService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UserService;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginUserTest extends RestTestRunner {
    private static final short tokenLength = 32;

    @DataProvider(name = "existingUserDataProvider")
    public Object[] getExistingUser(){
        Object[] user = {UserRepository.getValidUser()};
        return user;
    }

    @DataProvider(name = "existingAdminDataProvider")
    public Object[] getExistingAdmin(){
        Object[] admin = {UserRepository.getAdmin()};
        return admin;
    }

    @DataProvider(name = "nonExistingUserDataProvider")
    public Object[][] getNonExistingUser(){
        Object[][] user = {{UserRepository.getAdmin(), UserRepository.getNonExistingUser()}};
        return user;
    }

    @DataProvider(name = "nonExistingAdminDataProvider")
    public Object[][] getNonExistingAdmin(){
        Object[][] admin = {{UserRepository.getAdmin(), UserRepository.getNonExistingAdmin()}};
        return admin;
    }




    @Test(dataProvider = "existingUserDataProvider")
    public void loginExistingUserTest(User user){
        UserService userService = new LoginService()
                .successfulUserLogin(user);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }

    @Test(dataProvider = "existingAdminDataProvider")
    public void loginExistingAdminTest(User admin){
        AdministrationService adminService = new LoginService()
                .successfulAdminLogin(admin);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }



    @Test(dataProvider = "existingUserDataProvider")
    public void logoutExistingUserTest(User existingUser){
        SimpleEntity result = new LoginService()
                .successfulUserLogin(existingUser)
                .goToLoginService()
                .successfulLogout(ApplicationState.get().getLastLoggined());

        Assert.assertTrue(Boolean.valueOf(result.getContent()));
    }

    @Test(dataProvider = "nonExistingUserDataProvider")
    public void createAndLoginUserTest(User admin, User newUser){
        UserService userService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .createUser(newUser)
                .goToLoginService()
                .successfulUserLogin(newUser);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }

    @Test(dataProvider = "nonExistingAdminDataProvider")
    public void createAndLoginAdminTest(User admin, User newAdmin){
        AdministrationService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .removeUser(UserRepository.getValidUser());
                /*.createUser(newAdmin)
                .goToLoginService()
                .successfulAdminLogin(newAdmin);*/
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }

}

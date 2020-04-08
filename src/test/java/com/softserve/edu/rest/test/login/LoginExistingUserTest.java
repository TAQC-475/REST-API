package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.AdministrationService;
import com.softserve.edu.rest.services.LogginedUsersService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UserService;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class LoginExistingUserTest extends LoginTestRunner{

    @Test(dataProvider = "existingUserDataProvider", dataProviderClass = UsersTestData.class)
    public void loginExistingUserTest(User adminUser, User user){
        UserService userService = new LoginService()
                .successfulUserLogin(user);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }

    @Test(dataProvider = "existingAdminDataProvider", dataProviderClass = UsersTestData.class)
    public void loginExistingAdminTest(User admin){
        AdministrationService adminService = new LoginService()
                .successfulAdminLogin(admin);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }



    @Test(dataProvider = "existingUserDataProvider", dataProviderClass = UsersTestData.class)
    public void logoutExistingUserTest(User admin, User existingUser){
        List<User> users = new LoginService()
                .successfulUserLogin(existingUser)
                .gotoLoginService()
                .successfulLogout(ApplicationState.get().getLastLoggined())
                .gotoLoginService()
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService()
                .getLoggedUsers();

        Assert.assertFalse(users.contains(existingUser));
    }

    @Test(dataProvider = "existingUserDataProvider", dataProviderClass = UsersTestData.class)
    public void logoutExistingUserTestAsEntity(User admin, User existingUser){
        SimpleEntity simpleEntity = new LoginService()
                .successfulUserLogin(existingUser)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLoggined());
        Assert.assertTrue(Boolean.valueOf(simpleEntity.getContent()));
    }



    @Test(dataProvider = "existingUsersDataProvider", dataProviderClass = UsersTestData.class)
    public void loginExistingUsersTest(User admin, List<User> existingUsers){
        LogginedUsersService logginedUsersService = new LoginService()
                .successfulUsersLogin(existingUsers)
                .gotoLoginService()
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService();

        Assert.assertTrue(logginedUsersService.getLoggedUsers().containsAll(existingUsers) );
    }

}

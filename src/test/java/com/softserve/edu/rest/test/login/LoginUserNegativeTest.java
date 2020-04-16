package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.services.LogginedUsersService;
import com.softserve.edu.rest.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginUserNegativeTest extends LoginTestRunner{
    private static Logger logger = LoggerFactory.getLogger(LoginUserNegativeTest.class);

    @Test(dataProviderClass = UsersTestData.class, dataProvider = "nonExistingUserDataProvider",
    description = "This test verifies that error message appear when user try to login with invalid credentials")
    public void loginInvalidUserAsEntityTest(User admin, User notExisingUser){
        logger.info("Unsuccessful user login with credentials {}", notExisingUser);
        SimpleEntity simpleEntity = new GuestService()
                .gotoLoginService()
                .unsuccessfulUserLoginAsEntity(notExisingUser);

        Assert.assertEquals(LoginService.INVALID_USER, simpleEntity.getContent());
    }

    @Test(dataProviderClass = UsersTestData.class, dataProvider = "nonExistingUserDataProvider",
            description = "This test verifies that after unsuccessful login attempt user doesn't appear in the loggened users list")
    public void loginInvalidUserTest(User admin, User notExisingUser){
        logger.info("Unsuccessful user login with credentials {}", notExisingUser);
        LogginedUsersService logginedUsersService = new GuestService()
                .gotoLoginService()
                .unsuccessfulUserLogin(notExisingUser)
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService();
        Assert.assertFalse(logginedUsersService.getLogginedUsers().contains(notExisingUser));
    }

    @Test(dataProviderClass = UsersTestData.class, dataProvider = "nonExistingAdminDataProvider",
            description = "This test verifies that after unsuccessful login attempt admin doesn't appear in the loggened admins list")
    public void loginInvalidAdminTest(User admin, User notExisingAdmin){
        logger.info("Unsuccessful admin login with credentials {}", notExisingAdmin);
        LogginedUsersService logginedUsersService = new GuestService()
                .gotoLoginService()
                .unsuccessfulUserLogin(notExisingAdmin)
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService();
        Assert.assertFalse(logginedUsersService.getLogginedUsers().contains(notExisingAdmin));
    }

    @Test(dataProviderClass = UsersTestData.class, dataProvider = "existingAdminDataProvider",
    description = "This test verifies that error message appears after admin try to logout with invalid token")
    public void logoutWithInvalidTokenTest(User admin) {
        logger.info("Unsuccessful admin logout with credentials {}", admin);
        SimpleEntity errorMessage = new LoginService()
                .successfulAdminLogin(admin)
                .goToLoginService()
                .unsuccessfulLogoutAsEntity(ApplicationState.get().getLastLogged());
        Assert.assertFalse(Boolean.valueOf(errorMessage.getContent()));
    }
}

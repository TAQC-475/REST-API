package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LoginResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoginService {

    public static final Logger logger = LoggerFactory.getLogger(LockService.class);

    private LoginResource loginResource;
    public static final String INVALID_USER = "ERROR, user not found";

    public LoginService() {
        this.loginResource = new LoginResource();
    }

    @Step("Login")
    private SimpleEntity login(User user) {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName())
                .addParameter(EParameters.PASSWORD, user.getPassword());
        SimpleEntity tokenContent = loginResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(tokenContent);
        return tokenContent;
    }

    @Step("Successful Admin Login")
    public AdministrationService successfulAdminLogin(User adminUser) {
        ApplicationState.get().addUser((new LoginedUser(adminUser, login(adminUser).getContent())));
        return new AdministrationService(ApplicationState.get().getLastLoggined());
    }

    @Step("Successful User Login")
    public UserService successfulUserLogin(User basicUser) {
        ApplicationState.get().addUser(new LoginedUser(basicUser, login(basicUser).getContent()));
        return new UserService(ApplicationState.get().getLastLoggined());
    }

    @Step("Unsuccessful User Login")
    public LoginService unsuccessfulUserLogin(User basicUser) {
        login(basicUser);
        logger.debug("unsuccessful login by user = "+basicUser.getName());
        return this;
    }

    @Step("Unsuccessful User Login")
    public SimpleEntity unsuccessfulUserLoginAsEntity(User basicUser) {
        return login(basicUser);
    }

    @Step("Successful Admins Login")
    public AdministrationService successfulAdminsLogin(List<User> adminUsers) {
        for (User adminUser : adminUsers) {
            ApplicationState.get().addUser(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new AdministrationService((ApplicationState.get().getLastLoggined()));
    }

    @Step("Successful Users Login")
    public UserService successfulUsersLogin(List<User> adminUsers) {
        for (User adminUser : adminUsers) {
            ApplicationState.get().addUser(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new UserService((ApplicationState.get().getLastLoggined()));
    }

    private SimpleEntity logout(LoginedUser loginedUser) {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.NAME, loginedUser.getUser().getName())
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        SimpleEntity result = loginResource.httpDeleteAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(result);
        return result;
    }

    @Step("successful logout")
    public GuestService successfulLogout(LoginedUser loginedUser) {
        ApplicationState.get().removeLoggined(loginedUser);
        logout(loginedUser);
        return new GuestService();
    }

    @Step("successful logout")
    public SimpleEntity successfulLogoutAsEntity(LoginedUser loginedUser) {
        ApplicationState.get().removeLoggined(loginedUser);
        return logout(loginedUser);
    }

    public SimpleEntity changePasswordAndLogOut(User user, User oldPassword, String newPassword) {
        return new LoginService()
                .successfulUserLogin(user)
                .changePassword(oldPassword, newPassword)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLoggined());
    }

    public SimpleEntity successfulLoginAndLogout(User baseUser) {
        return new LoginService().successfulUserLogin(baseUser)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLoggined());
    }

    public GuestService successfulUsersLogout(List<LoginedUser> loginedUsers) {
        for (int i = 0; i < loginedUsers.size(); ++i) {
            LoginedUser loginedUser = loginedUsers.get(i);
            ApplicationState.get().removeLoggined(loginedUser);
            logout(loginedUser);
        }
        return new GuestService();
    }

//    public SimpleEntity unsuccessfulUserLogin(User user){
//        return login(user);
//    }


}

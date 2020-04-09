package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LoginResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoginService {

    private LoginResource loginResource;
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    public static final String INVALID_USER = "ERROR, user not found";

    public LoginService() {
        this.loginResource = new LoginResource();
    }

    private SimpleEntity login(User user) {
        logger.debug("Login method receive {}", user);
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName())
                .addParameter(EParameters.PASSWORD, user.getPassword());
        SimpleEntity tokenContent = loginResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(tokenContent);
        logger.debug("Login method returns token {}", tokenContent);
        return tokenContent;
    }

    @Step("Successful Admin Login")
    public AdministrationService successfulAdminLogin(User adminUser) {
        ApplicationState.get().addUser((new LogginedUser(adminUser, login(adminUser).getContent())));
        return new AdministrationService(ApplicationState.get().getLastLoggined());
    }

    @Step("Successful User Login")
    public UserService successfulUserLogin(User basicUser) {
        ApplicationState.get().addUser(new LogginedUser(basicUser, login(basicUser).getContent()));
        return new UserService(ApplicationState.get().getLastLoggined());
    }

    @Step("Unsuccessful User Login")
    public LoginService unsuccessfulUserLogin(User basicUser) {
        login(basicUser);
        logger.debug("Unsuccessful login by user {}", basicUser.getName());
        return this;
    }

    @Step("Unsuccessful User Login")
    public SimpleEntity unsuccessfulUserLoginAsEntity(User basicUser) {
        logger.warn("Unsuccessful User Login {}", basicUser.toString());
        return login(basicUser);
    }

    @Step("Successful Admins Login")
    public AdministrationService successfulAdminsLogin(List<User> adminUsers) {
        for (User adminUser : adminUsers) {
            ApplicationState.get().addUser(new LogginedUser(adminUser, login(adminUser).getContent()));
        }
        return new AdministrationService((ApplicationState.get().getLastLoggined()));
    }

    @Step("Successful Users Login")
    public UserService successfulUsersLogin(List<User> adminUsers) {
        logger.debug("Successful Users Login {}", adminUsers.toString());
        for (User adminUser : adminUsers) {
            ApplicationState.get().addUser(new LogginedUser(adminUser, login(adminUser).getContent()));
        }
        return new UserService((ApplicationState.get().getLastLoggined()));
    }

    private SimpleEntity logout(LogginedUser logginedUser) {
        logger.debug("Logout for loggined user {}", logginedUser);
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.NAME, logginedUser.getUser().getName())
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity result = loginResource.httpDeleteAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(result);
        return result;
    }

    @Step("Successful logout")
    public GuestService successfulLogout(LogginedUser logginedUser) {
        ApplicationState.get().removeLoggined(logginedUser);
        logout(logginedUser);
        return new GuestService();
    }

    @Step("Successful logout")
    public SimpleEntity successfulLogoutAsEntity(LogginedUser logginedUser) {
        ApplicationState.get().removeLoggined(logginedUser);
        return logout(logginedUser);
    }

    @Step("Unsuccessful logout as entity")
    public SimpleEntity unsuccessfulLogoutAsEntity(LogginedUser logginedUser){
        return logout(logginedUser);
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

    public GuestService successfulUsersLogout(List<LogginedUser> logginedUsers) {
        for (int i = 0; i < logginedUsers.size(); ++i) {
            LogginedUser logginedUser = logginedUsers.get(i);
            ApplicationState.get().removeLoggined(logginedUser);
            logout(logginedUser);
        }
        return new GuestService();
    }


}

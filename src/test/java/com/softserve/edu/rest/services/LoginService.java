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
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private LoginResource loginResource;
    public static final String INVALID_USER = "ERROR, user not found";

    public LoginService() {
        this.loginResource = new LoginResource();
    }

    /**
     * POST request for user login
     *
     * @param user user for login to system
     * @return if true token else false
     */
    private SimpleEntity login(User user) {
        LOGGER.debug("Login method receive: Username = {}, password = {}", user.getName(), user.getPassword());
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName())
                .addParameter(EParameters.PASSWORD, user.getPassword());
        SimpleEntity tokenContent = loginResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(tokenContent);
        LOGGER.debug("Login method returns token {} and status code {}", tokenContent.getContent(), tokenContent.getCode());
        return tokenContent;
    }

    private String changeToken(String token) {
        return token.replace(token.charAt(0), '_');
    }

    /**
     * Successful Admin Login
     *
     * @param adminUser admin user for login to system
     * @return new Administration Service
     */
    @Step("Successful Admin Login")
    public AdministrationService successfulAdminLogin(User adminUser) {
        LOGGER.debug("Successful Admin login: Username = {}, password = {}", adminUser.getName(), adminUser.getPassword());
        ApplicationState.get().addUser((new LogginedUser(adminUser, login(adminUser).getContent())));
        return new AdministrationService(ApplicationState.get().getLastLogged());
    }

    /**
     * Successful User Login
     *
     * @param basicUser user for login to system
     * @return new User Service
     */
    @Step("Successful User Login")
    public UserService successfulUserLogin(User basicUser) {
        LOGGER.debug("Successful login: Username = {}, password = {}", basicUser.getName(), basicUser.getPassword());
        ApplicationState.get().addUser(new LogginedUser(basicUser, login(basicUser).getContent()));
        return new UserService(ApplicationState.get().getLastLogged());
    }

    /**
     * Unsuccessful User Login
     *
     * @param basicUser user for login to system
     * @return Login Service
     */
    @Step("Unsuccessful User Login")
    public LoginService unsuccessfulUserLogin(User basicUser) {
        login(basicUser);
        LOGGER.warn("Unsuccessful login by: {}", basicUser.getName());
        return this;
    }

    @Step("Unsuccessful User Login")
    public SimpleEntity unsuccessfulUserLoginAsEntity(User basicUser) {
        LOGGER.warn("Unsuccessful login {}", basicUser.getName());
        return login(basicUser);
    }

    @Step("Successful Admins Login")
    public AdministrationService successfulAdminsLogin(List<User> adminUsers) {
        LOGGER.debug("Successful Admins list Login: {}", adminUsers);
        for (User adminUser : adminUsers) {
            ApplicationState.get().addUser(new LogginedUser(adminUser, login(adminUser).getContent()));
        }
        return new AdministrationService((ApplicationState.get().getLastLogged()));
    }

    @Step("Successful Users Login")
    public UserService successfulUsersLogin(List<User> adminUsers) {
        LOGGER.debug("Successful Users list Login: {}", adminUsers.toString());
        for (User adminUser : adminUsers) {
            ApplicationState.get().addUser(new LogginedUser(adminUser, login(adminUser).getContent()));
        }
        return new UserService((ApplicationState.get().getLastLogged()));
    }

    /**
     * DELETE request for user logout
     *
     * @param loggedUser user for logout from the system
     * @return if true content = "true" and status code = "200" else false
     */
    private SimpleEntity logout(LogginedUser loggedUser) {
        LOGGER.debug("Logout for:  UserName = {}, with token = {}", loggedUser.getUser().getName(), loggedUser.getToken());
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.NAME, loggedUser.getUser().getName())
                .addParameter(EParameters.TOKEN, loggedUser.getToken());
        SimpleEntity result = loginResource.httpDeleteAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(result);
        return result;
    }

    /**
     * Successful User logout
     *
     * @param loggedUser user for logout from the system
     * @return new Guest Service
     */
    @Step("Successful logout")
    public GuestService successfulLogout(LogginedUser loggedUser) {
        LOGGER.debug("Successful logout: UserName = {}, with token = {}", loggedUser.getUser().getName(), loggedUser.getToken());
        ApplicationState.get().removeLogged(loggedUser);
        logout(loggedUser);
        return new GuestService();
    }

    /**
     * Successful User logout
     *
     * @param loggedUser user for logout from the system
     * @return content "true" and status code "200"
     */
    @Step("Successful logout")
    public SimpleEntity successfulLogoutAsEntity(LogginedUser loggedUser) {
        LOGGER.debug("Successful logout: UserName = {}, with token = {}", loggedUser.getUser().getName(), loggedUser.getToken());
        ApplicationState.get().removeLogged(loggedUser);
        return logout(loggedUser);
    }

    @Step("Unsuccessful logout as entity")
    public SimpleEntity unsuccessfulLogoutAsEntity(LogginedUser logginedUser) {
        LOGGER.debug("Unsuccessful logout: UserName = {}, with token = {}", logginedUser.getUser().getName(), logginedUser.getToken());
        return logout(new LogginedUser(logginedUser.getUser(), changeToken(logginedUser.getToken())));
    }

    /**
     * Login, change user password and logout
     *
     * @param user        user for login to the system
     * @param oldPassword actual user password
     * @param newPassword new user password
     * @return new Login Service
     */
    public SimpleEntity changePasswordAndLogOut(User user, User oldPassword, String newPassword) {
        return new LoginService()
                .successfulUserLogin(user)
                .changePassword(oldPassword, newPassword)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLogged());
    }

    /**
     * User login and logout
     *
     * @param baseUser user for login and logout
     * @return new Login Service
     */
    public SimpleEntity successfulLoginAndLogout(User baseUser) {
        return new LoginService().successfulUserLogin(baseUser)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLogged());
    }

    /**
     * User login and logout
     *
     * @param admin login as administrator
     * @param newUser data for creating new user
     * @return new Login Service
     */
    public AdministrationService loginAndCreateUser(User admin, User newUser) {
        return new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .createUser(newUser)
                ;
    }

    public GuestService successfulUsersLogout(List<LogginedUser> loggedUsers) {
        LOGGER.debug("Successful Users list Logout: {}", loggedUsers);
        for (int i = 0; i < loggedUsers.size(); ++i) {
            LogginedUser logginedUser = loggedUsers.get(i);
            ApplicationState.get().removeLogged(logginedUser);
            logout(logginedUser);
        }
        return new GuestService();
    }
}
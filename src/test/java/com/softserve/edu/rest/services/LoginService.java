package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LoginResource;
import com.softserve.edu.rest.tools.EntityUtils;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class LoginService {
    private LoginResource loginResource;
    public static final String INVALID_USER = "ERROR, user not found";

    public LoginService() {
        this.loginResource = new LoginResource();
    }

    private SimpleEntity login(User user) {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName())
                .addParameter(EParameters.PASSWORD, user.getPassword());
        SimpleEntity tokenContent = loginResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(tokenContent);
        return tokenContent;
    }

    public AdministrationService successfulAdminLogin(User adminUser){
        ApplicationState.get().addUser((new LoginedUser(adminUser, login(adminUser).getContent())));
        return new AdministrationService(ApplicationState.get().getLastLoggined());
    }

    public UserService successfulUserLogin(User basicUser){
        ApplicationState.get().addUser(new LoginedUser(basicUser, login(basicUser).getContent()));
        return new UserService(ApplicationState.get().getLastLoggined());
    }



    public AdministrationService successfulAdminsLogin(List<User> adminUsers) {
        for (User adminUser : adminUsers) {
            ApplicationState.get().addUser(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new AdministrationService((ApplicationState.get().getLastLoggined()));
    }

    public UserService successfulUsersLogin(List<User> adminUsers){
        for (User adminUser : adminUsers){
            ApplicationState.get().addUser(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new UserService((ApplicationState.get().getLastLoggined()));
    }

    private SimpleEntity logout(LoginedUser loginedUser){
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.NAME, loginedUser.getUser().getName())
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        SimpleEntity result = loginResource.httpDeleteAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(result);
        return result;
    }

    public GuestService successfulLogout(LoginedUser loginedUser){
        ApplicationState.get().removeLoggined(loginedUser);
        logout(loginedUser);
        return new GuestService();
    }

    public SimpleEntity unsuccessfulUserLogin(User user){
        return login(user);
    }

}

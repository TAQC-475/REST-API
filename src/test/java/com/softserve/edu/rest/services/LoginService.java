package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.LogginedUsers;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LoginResource;
import com.softserve.edu.rest.tools.EntityUtils;

import java.util.List;

public class LoginService {
    private LoginResource loginResource;

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
        LogginedUsers.get().addUser((new LoginedUser(adminUser, login(adminUser).getContent())));
        return new AdministrationService(LogginedUsers.get().getLastLoggined());
    }

    public UserService successfulUserLogin(User basicUser){
        LogginedUsers.get().addUser(new LoginedUser(basicUser, login(basicUser).getContent()));
        return new UserService(LogginedUsers.get().getLastLoggined());
    }



    public AdministrationService successfulAdminsLogin(List<User> adminUsers) {
        for (User adminUser : adminUsers) {
            LogginedUsers.get().addUser(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new AdministrationService((LogginedUsers.get().getLastLoggined()));
    }

    public UserService successfulUsersLogin(List<User> adminUsers){
        for (User adminUser : adminUsers){
            LogginedUsers.get().addUser(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new UserService(LogginedUsers.get().getLastLoggined());
    }

}

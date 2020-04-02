package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LoginResource;
import com.softserve.edu.rest.tools.EntityUtils;

import java.util.LinkedList;
import java.util.List;

public class LoginService {
    private LoginResource loginResource;
    private LinkedList<LoginedUser> logginedClients;

    public LoginService() {
        this.loginResource = new LoginResource();
        this.logginedClients = new LinkedList<>();
    }

    private SimpleEntity login(User user) {
        RestParameters bodyParameters = new RestParameters()
                .addParameter("name", user.getName())
                .addParameter("password", user.getPassword());
        SimpleEntity tokenContent = loginResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(tokenContent);
        return tokenContent;
    }

    public AdministrationService successfulAdminLogin(User adminUser){
        logginedClients.add(new LoginedUser(adminUser, login(adminUser).getContent()));
        return new AdministrationService(logginedClients.getLast());
    }

    public UserService successfulUserLogin(User basicUser){
        logginedClients.add(new LoginedUser(basicUser, login(basicUser).getContent()));
        return new UserService(logginedClients.getLast());
    }



    public AdministrationService successfulAdminsLogin(List<User> adminUsers) {
        for (User adminUser : adminUsers) {
            logginedClients.add(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new AdministrationService((logginedClients.getLast()));
    }

    public UserService successfulUsersLogin(List<User> adminUsers){
        for (User adminUser : adminUsers){
            logginedClients.add(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new UserService(logginedClients.getLast());
    }

}

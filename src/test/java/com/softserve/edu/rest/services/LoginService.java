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
    private LinkedList<LoginedUser> loginedClients;

    public LoginService() {
        this.loginResource = new LoginResource();
        this.loginedClients = new LinkedList<>();
    }

    private SimpleEntity login(User user){
        RestParameters bodyParameters = new RestParameters()
                .addParameter("name", user.getName())
                .addParameter("password", user.getPassword());
        SimpleEntity tokenContent = loginResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(tokenContent);
        return tokenContent;
    }

    public AdministrationService successfulAdminLogin(User adminUser){
        loginedClients.add(new LoginedUser(adminUser, login(adminUser).getContent()));
        return new AdministrationService(loginedClients.getLast());
    }

    public UserService successfulUserLogin(User basicUser){
        loginedClients.add(new LoginedUser(basicUser, login(basicUser).getContent()));
        return new UserService(loginedClients.getLast());
    }



    public AdministrationService successfulAdminsLogin(List<User> adminUsers){
        for (User adminUser : adminUsers){
            loginedClients.add(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new AdministrationService(loginedClients.getLast());
    }

    public UserService successfulUsersLogin(List<User> adminUsers){
        for (User adminUser : adminUsers){
            loginedClients.add(new LoginedUser(adminUser, login(adminUser).getContent()));
        }
        return new UserService((loginedClients.getLast()));
    }


    //public logout()...

}

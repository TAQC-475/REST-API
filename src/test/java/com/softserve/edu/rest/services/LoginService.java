package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LoginResource;
import com.softserve.edu.rest.tools.EntityUtils;

public class LoginService {
    private LoginResource loginResource;

    public LoginService() {
        this.loginResource = new LoginResource();
    }

    public AdministrationService successfulAdmin(User adminUser){
        RestParameters bodyParameters = new RestParameters()
                .addParameter("name", adminUser.getName())
                .addParameter("password", adminUser.getPassword());
        SimpleEntity adminContent = loginResource.httpPostAsEntity(null, null, bodyParameters);
        if (adminContent.getContent() == ""
                || adminContent.getContent() == "false"
                || adminContent.getContent() == "null"){
            throw new RuntimeException("Admin logined error, name = " + adminUser.getName());
        }
        return new AdministrationService(new LoginedUser(adminUser, adminContent.getContent()));
    }

    public UserService successfulUser(User user){
        RestParameters bodyParameters = new RestParameters()
                .addParameter("name", user.getName())
                .addParameter("password", user.getPassword());
        SimpleEntity userContent = loginResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(userContent);
        return new UserService(new LoginedUser(user, userContent.getContent()));
    }

    //public successfulUserLogin()...

    //public successfulAdminLogin()...

    //public logout()...

}

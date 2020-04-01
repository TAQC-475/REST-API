package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LoginResource;
import com.softserve.edu.rest.resources.LoginResource;
import javax.jws.soap.SOAPBinding.Use;

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

    //public successfulUserLogin()...

    //public successfulAdminLogin()...

    //public logout()...

}

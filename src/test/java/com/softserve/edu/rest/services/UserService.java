package com.softserve.edu.rest.services;


import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.UserResource;
import com.softserve.edu.rest.tools.CustomException;
import com.softserve.edu.rest.tools.EntityUtils;

public class UserService {
    private UserResource userResource;
    private LoginedUser loginedUser;

    public UserService(LoginedUser loginedUser) {
        userResource = new UserResource();
        this.loginedUser = loginedUser;
    }

    public ItemService goToItemService() {
        return new ItemService(loginedUser);
    }

    public ItemsService goToItemsService() {
        return new ItemsService(loginedUser);
    }

    public LoginService goToLoginService() {
        return new LoginService();
    }

    public UserService changePassword(String oldPassword, String newPassword) {
    public AdministrationService gotoAdministrationService(){
        AdministrationService administrationService = null;
        try {
             administrationService = new AdministrationService(ApplicationState.get().getAdmin());
        }catch (CustomException exception){
            exception.printStackTrace();
        }
        return administrationService;
    }

//    public boolean changePassword(User oldPassword, User newPassword){
//        RestParameters bodyParameters = new RestParameters()
//                .addParameter(EParameters.TOKEN, loginedUser.getToken())
//                .addParameter(EParameters.OLD_PASSWORD, oldPassword.getPassword())
//                .addParameter(EParameters.NEW_PASSWORD, newPassword.getPassword());
//        SimpleEntity isChanged = userResource.httpPutAsEntity(null, null, bodyParameters);
//        return isChanged;
//    }


//    public boolean createUser(User user){
//        RestParameters bodyParameters = new RestParameters()
//                .addParameter(EParameters.TOKEN, loginedUser.getToken())
//                .addParameter(EParameters.NAME, user.getName())
//                .addParameter(EParameters.PASSWORD, user.getPassword())
//                .addParameter(EParameters.RIGHTS, String.valueOf(user.isAdmin()));
//        SimpleEntity simpleEntity = userResource.httpPostAsEntity(null, null, bodyParameters);
//        EntityUtils.get().checkEntity(simpleEntity);
//    }






    public UserService changePassword(User oldPassword, String newPassword){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken())
                .addParameter(EParameters.OLD_PASSWORD, oldPassword)
                .addParameter(EParameters.NEW_PASSWORD, newPassword);
        SimpleEntity result = userResource.httpPutAsEntity(null, urlParameters, null);
        EntityUtils.get().checkEntity(result);
        return this;
    }
}
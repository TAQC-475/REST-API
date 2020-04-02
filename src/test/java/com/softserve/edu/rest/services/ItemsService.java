package com.softserve.edu.rest.services;


import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ItemsResource;
import com.softserve.edu.rest.resources.UserItemsResource;
import com.softserve.edu.rest.tools.EntityUtils;

public class ItemsService {
    private LoginedUser loginedUser;
    private ItemsResource itemsResource;
    private UserItemsResource userItemsResource;

    public ItemsService(LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
        itemsResource = new ItemsResource();
        userItemsResource = new UserItemsResource();
    }

    public String getAllItems(){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN.toString(), loginedUser.getToken());
        SimpleEntity result = itemsResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(result);
        return result.getContent();
    }

    public String getAllUserItemsAsAdmin(User user){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN.toString(), loginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME.toString(), user.getName());
        SimpleEntity result = userItemsResource.httpGetAsEntity(pathParameters, urlParameters);
        EntityUtils.get().checkEntity(result);
        return result.getContent();
    }
}

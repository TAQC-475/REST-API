package com.softserve.edu.rest.services;


import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ItemsResource;
import com.softserve.edu.rest.tools.EntityUtils;

public class ItemsService {
    private LoginedUser loginedUser;
    private ItemsResource itemsResource;

    public ItemsService(LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
        itemsResource = new ItemsResource();
    }

    public String getAllItems(){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN.toString(), loginedUser.getToken());
        SimpleEntity result = itemsResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(result);
        return result.getContent();
    }
}

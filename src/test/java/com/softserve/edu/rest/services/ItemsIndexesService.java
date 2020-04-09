package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ItemsIndexesResource;
import com.softserve.edu.rest.tools.EntityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsIndexesService {
    private ItemsIndexesResource itemsIndexesResource;
    private LogginedUser logginedUser;

    public ItemsIndexesService(LogginedUser logginedUser) {
        this.logginedUser = logginedUser;
        itemsIndexesResource = new ItemsIndexesResource();
    }

    public List<String> getAllItemsIndexes(){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity indexes = itemsIndexesResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(indexes);
        return new ArrayList<>(Arrays.asList(indexes.getContent().split(" ")));
    }
}

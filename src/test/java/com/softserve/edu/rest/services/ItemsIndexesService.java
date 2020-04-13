package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ItemsIndexesResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsIndexesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsIndexesService.class);
    private ItemsIndexesResource itemsIndexesResource;
    private LogginedUser logginedUser;

    public ItemsIndexesService(LogginedUser logginedUser) {
        this.logginedUser = logginedUser;
        itemsIndexesResource = new ItemsIndexesResource();
    }

    /**
     * Prepares and sends GET request to get logged in user all item indexes
     * @return indexes of all user items
     */
    @Step("Get all user items indexes")
    public List<String> getAllItemsIndexes(){
        LOGGER.debug("User = {} getting all items indexes", logginedUser.getUser().getName());
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity indexes = itemsIndexesResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(indexes);
        LOGGER.debug("User = {} got indexes = {}", logginedUser.getUser().getName(), indexes.getContent());
        return new ArrayList<>(Arrays.asList(indexes.getContent().split(" ")));
    }
}

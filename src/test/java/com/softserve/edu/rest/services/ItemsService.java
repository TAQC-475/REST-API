package com.softserve.edu.rest.services;


import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ItemsResource;
import com.softserve.edu.rest.resources.UserItemsResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsService.class);
    private LogginedUser logginedUser;
    private ItemsResource itemsResource;
    private UserItemsResource userItemsResource;

    public ItemsService(LogginedUser logginedUser) {
        this.logginedUser = logginedUser;
        itemsResource = new ItemsResource();
        userItemsResource = new UserItemsResource();
    }

    /**
     * Prepares and sends GET request to get all logged in user items
     * @return all logged in user items
     */
    @Step("Items service: got all items")
    public String getAllItems() {
        LOGGER.debug("User = {} trying to get all his items", logginedUser.getUser().getName());
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity result = itemsResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(result);
        LOGGER.debug("User = {} got items = {}", logginedUser.getUser().getName(), result);
        return result.getContent();
    }

    /**
     * Prepares and sends GET request to get all logged in user items status code
     * @return status code of get all user items request
     */
    public String getAllItemsStatusCode() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity statusCode = itemsResource.httpGetAsEntity(null, urlParameters);
        return statusCode.getCode();
    }

    /**
     * Prepares and sends GET request to get all items of user as admin
     * @param user user whose items request gets
     * @return all items of user
     */
    @Step("Items service: got all user {user} items as admin")
    public String getAllUserItemsAsAdmin(User user) {
        LOGGER.debug("Admin = {} trying to get items of user = {}", logginedUser.getUser().getName(), user.getName());
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName());
        SimpleEntity result = userItemsResource.httpGetAsEntity(pathParameters, urlParameters);
        EntityUtils.get().checkEntity(result);
        LOGGER.debug("Admin = {} got items = {}", logginedUser.getUser().getName(), result.getContent());
        return result.getContent();
    }

    /**
     * Prepares and sends GET request to get all items of user as admin status code
     * @param user user whose items request gets
     * @return status code of get all items of user as admin request
     */
    public String getAllUserItemsAsAdminStatusCode(User user) {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName());
        SimpleEntity statusCode = userItemsResource.httpGetAsEntity(pathParameters, urlParameters);
        return statusCode.getCode();
    }

    /**
     * Parse string value of user items into list of items
     * @param items String value of user items
     * @return list of user items
     */
    @Step("Items service: got list of items {items}")
    public List<Item> getItemsList(String items) {
        LOGGER.debug("Converting string = {} to list of items", items);
        List<Item> itemsList = new ArrayList<>();
        Pattern pattern = Pattern.compile("(.+) \t(.+)\n");
        Matcher matcher = pattern.matcher(items);
        while (matcher.find()){
            itemsList.add(new Item(matcher.group(1), matcher.group(2)));
        }
        LOGGER.debug("String was converted to list = {}", itemsList.toString());
        return itemsList;
    }

    /**
     * gets list of all user items
     * @return list of all user items
     */
    @Step("Items service: got list of all items")
    public List<Item> getAllItemsList() {
        return getItemsList(getAllItems());
    }
}

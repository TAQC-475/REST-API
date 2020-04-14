package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ItemsIndexesResource;
import com.softserve.edu.rest.resources.ItemResource;
import com.softserve.edu.rest.resources.UserItemResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    private LogginedUser logginedUser;
    private ItemResource itemResource;
    private ItemsIndexesResource itemsIndexesResource;
    private UserItemResource userItemResource;

    public ItemService(LogginedUser logginedUser) {
        this.logginedUser = logginedUser;
        itemResource = new ItemResource();
        itemsIndexesResource = new ItemsIndexesResource();
        userItemResource = new UserItemResource();
    }

    private List<Integer> parseIndexes(String indexes) {
        List<Integer> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(indexes);
        while (matcher.find()) {
            result.add(Integer.valueOf(indexes.substring(matcher.start(), matcher.end())));
        }
        return result;
    }

    /**
     * get indexes of all items of logged in user and checks if index from params is available
     * @param index index to check for availability
     * @return true if index is available, false if item with such index already exists
     */
    private boolean isIndexFree(int index){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity itemsIndexes = itemsIndexesResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(itemsIndexes);
        boolean isFree = true;
        for(Integer current: parseIndexes(itemsIndexes.getContent())){
            if (current == index) {
                isFree = false;
                break;
            }
        }
        return isFree;
    }
    
    public ItemService overrideItem(Item item) {
        return addItem(item, true);
    }

    @Step("Overwrite user's item (PUT)")
    public ItemService putOverwriteItem(Item initialItem, Item overwtriteItem) {
        LOGGER.debug("User trying to owerwrite(PUT method) his initial item = {} to new item ={}" , initialItem, overwtriteItem);
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken())
                .addParameter(EParameters.ITEM, overwtriteItem.getItemText());

        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.INDEX, initialItem.getItemIndex());

        SimpleEntity result = itemResource
                .httpPutAsEntity(pathParameters, bodyParameters, null);
        EntityUtils.get().checkEntity(result);
        LOGGER.debug("putOvewriteItem method returns result = {} " , result);
        return this;
    }

    /**
     * Preparing and sending POST request as logged in user to add item
     * @param item item to add
     * @param toOverride to override item, if item with same index already exists?
     * @return ItemService after adding an item
     */
    @Step("Item Service: item added {item}")
    public ItemService addItem(Item item, boolean toOverride){
        LOGGER.debug("addItem method gets item = {} " , item);
        if(!toOverride && !isIndexFree(Integer.parseInt(item.getItemIndex()))){
            LOGGER.warn("RuntimeException");
            throw new RuntimeException("Item with such index already exists");
        }
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken())
                .addParameter(EParameters.ITEM, item.getItemText());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.INDEX, item.getItemIndex());
        SimpleEntity status = itemResource.httpPostAsEntity(pathParameters, null, bodyParameters);
        EntityUtils.get().checkEntity(status);
        LOGGER.debug("addItem method returns status = {} " , status);
        return this;
    }

    /**
     * Preparing and sending POST request as logged in user to add item and get response status code
     * @param item item to add
     * @param toOverride to override item, if item with same index already exists?
     * @return status code of adding item request
     */
    @Step("Get status code of add item request")
    public String getCreateItemRequestStatusCode(Item item, boolean toOverride){
        LOGGER.debug("Getting request code after adding item = {}", item);
        if(!toOverride && !isIndexFree(Integer.parseInt(item.getItemIndex()))){
            throw new RuntimeException("Item with such index already exists");
        }
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken())
                .addParameter(EParameters.ITEM, item.getItemText());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.INDEX, item.getItemIndex());
        SimpleEntity statusCode = itemResource.httpPostAsEntity(pathParameters, null, bodyParameters);
        LOGGER.debug("Adding item = {} status code = {}", item,  statusCode.getCode());
        return statusCode.getCode();
    }
    @Step("Getting item")
    public String getItem(Item item) {
        LOGGER.debug("User trying to get his item = {} " , item);
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.INDEX, item.getItemIndex());
        SimpleEntity result = itemResource.httpGetAsEntity(pathParameters, urlParameters);
        EntityUtils.get().checkEntity(result);
        LOGGER.debug("getItem method returns result = {} " , result.getContent());
        return result.getContent();
    }

    @Step("Getting user item by another user")
    public String getUserItemByAnotherUser(User user, Item item) {
        LOGGER.debug("User ={} trying to get another user item = {}" , user, item);
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName())
                .addParameter(EParameters.INDEX, item.getItemIndex());

        SimpleEntity result = userItemResource.httpGetAsEntity(pathParameters, urlParameters);
        EntityUtils.get().checkEntity(result);
        LOGGER.debug("getUserItemByAnotherUser method returns result = {} " , result.getContent());
        return result.getContent();
    }

    @Step("Delete item")
    public ItemService deleteItem(Item item) {
        LOGGER.debug("User trying to delete his item = {}", item);
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());

        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.INDEX, item.getItemIndex())
                .addParameter(EParameters.ITEM, item.getItemText());

        SimpleEntity result = itemResource.httpDeleteAsEntity(pathParameters, bodyParameters, null);
        LOGGER.debug("deleteItem method returns result = {} ", result.getContent());
        return this;
    }


    public UserService goToUserService() {
        return new UserService(logginedUser);
    }

    public ItemsService goToItemsService(){
        return new ItemsService(logginedUser);
    }

    public ItemsIndexesService goToItemsIndexesService(){ return new ItemsIndexesService(logginedUser);}
}

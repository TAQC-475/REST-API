package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ItemsIndexesResource;
import com.softserve.edu.rest.resources.ItemResource;
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

    public ItemService(LogginedUser logginedUser) {
        this.logginedUser = logginedUser;
        itemResource = new ItemResource();
        itemsIndexesResource = new ItemsIndexesResource();
    }

    private List<Integer> parseIndexes(String indexes){
        List<Integer> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(indexes);
        while (matcher.find()){
            result.add(Integer.valueOf(indexes.substring(matcher.start(),matcher.end())));
        }
        return result;
    }

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

    public ItemService overrideItem(Item item){
     return addItem(item, true);
    }

    @Step("Adding Item")
    public ItemService addItem(Item item, boolean toOverride){
        LOGGER.debug("addItem method gets: " + item.toString());
        if(!toOverride && !isIndexFree(Integer.valueOf(item.getItemIndex()))){
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
        LOGGER.debug("addItem method returns status: " + status);
        return this;
    }

    @Step("Get status code of add item request")
    public String getCreateItemRequestStatusCode(Item item, boolean toOverride){
        if(!toOverride && !isIndexFree(Integer.valueOf(item.getItemIndex()))){
            throw new RuntimeException("Item with such index already exists");
        }
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken())
                .addParameter(EParameters.ITEM, item.getItemText());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.INDEX, item.getItemIndex());
        SimpleEntity statusCode = itemResource.httpPostAsEntity(pathParameters, null, bodyParameters);
        return statusCode.getCode();
    }

    public Item getItem(Item item){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.INDEX, item.getItemIndex());
        SimpleEntity result = itemResource.httpGetAsEntity(pathParameters, urlParameters);
        EntityUtils.get().checkEntity(result);
        return new Item(item.getItemIndex(), result.getContent());
    }

    public UserService goToUserService(){
        return new UserService(logginedUser);
    }

    @Step("Go to Items service")
    public ItemsService goToItemsService(){
        return new ItemsService(logginedUser);
    }

    public ItemsIndexesService goToItemsIndexesService(){ return new ItemsIndexesService(logginedUser);}
}

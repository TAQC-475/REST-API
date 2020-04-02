package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ItemsIndexesResource;
import com.softserve.edu.rest.resources.ItemResource;
import com.softserve.edu.rest.tools.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemService {
    private LoginedUser loginedUser;
    private ItemResource itemResource;
    private ItemsIndexesResource itemsIndexesResource;

    public ItemService(LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
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
                .addParameter(EParameters.TOKEN.toString(), loginedUser.getToken());
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
     return createItem(item, true);
    }

    public ItemService createItem(Item item, boolean toOverride){
        if(!toOverride && !isIndexFree(Integer.valueOf(item.getItemIndex()))){
            throw new RuntimeException("Item with such index already exists");
        }
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN.toString(), loginedUser.getToken())
                .addParameter(EParameters.ITEM.toString(), item.getItemText());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.INDEX.toString(), item.getItemIndex());
        SimpleEntity status = itemResource.httpPostAsEntity(pathParameters, null, bodyParameters);
        EntityUtils.get().checkEntity(status);
        return this;
    }

    public Item getItem(Item item){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN.toString(), loginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.INDEX.toString(), item.getItemIndex());
        SimpleEntity result = itemResource.httpGetAsEntity(pathParameters, urlParameters);
        EntityUtils.get().checkEntity(result);
        return new Item(item.getItemIndex(), result.getContent());
    }

    public UserService goToUserService(){
        return new UserService(loginedUser);
    }

    public ItemsService goToItemsService(){
        return new ItemsService(loginedUser);
    }
}

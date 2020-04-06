package com.softserve.edu.rest.services;


import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ItemsResource;
import com.softserve.edu.rest.resources.UserItemsResource;
import com.softserve.edu.rest.tools.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemsService {
    private LoginedUser loginedUser;
    private ItemsResource itemsResource;
    private UserItemsResource userItemsResource;

    public ItemsService(LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
        itemsResource = new ItemsResource();
        userItemsResource = new UserItemsResource();
    }

    public String getAllItems() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        SimpleEntity result = itemsResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(result);
        return result.getContent();
    }

    public String getAllUserItemsAsAdmin(User user) {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName());
        SimpleEntity result = userItemsResource.httpGetAsEntity(pathParameters, urlParameters);
        System.out.println(result);
        EntityUtils.get().checkEntity(result);
        return result.getContent();
    }

    public List<Item> getItemsList(String items) {
        List<Item> itemsList = new ArrayList<>();
        List<String> indexes = new ArrayList<>();
        List<String> productText = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\d+\\s");
        Matcher matcher;
        for (String item : items.split("\n")) {
            matcher = pattern.matcher(item);
            while (matcher.find()) {
                indexes.add(item.substring(matcher.start(), matcher.end()).trim());
            }
        }

        pattern = Pattern.compile("\t.+\n");
        matcher = pattern.matcher(items);
        while (matcher.find()) {
            productText.add(items.substring(matcher.start() + 1, matcher.end() - 1));
        }
        for (int i = 0; i < indexes.size(); i++) {
            itemsList.add(new Item(indexes.get(i), productText.get(i)));
        }
        return itemsList;
    }

    public List<Item> getAllItemsList() {
        return getItemsList(getAllItems());
    }
}

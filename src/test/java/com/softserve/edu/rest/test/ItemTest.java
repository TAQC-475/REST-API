package com.softserve.edu.rest.test;


import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.ItemService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ItemTest {
    @DataProvider
    public Object[][] dataForItemTest(){
            return new Object[][]{{UserRepository.getValidUser(),
                    ItemRepository.getDefaultItem(), ItemRepository.getDefaultItem()}};
    }
    @DataProvider
    public Object[][] dataForUpdateItemTest(){
        return new Object[][]{{UserRepository.getAkimatcUser(),
                ItemRepository.getCoreI5(), ItemRepository.getCoreI7(), ItemRepository.getCoreI7()}};
    }

    @Test(dataProvider = "dataForItemTest")
    public void verifyUserCanCreateItem(User user, Item insertItem, Item checkItem){
        ItemService itemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .createItem(insertItem, false);
        Assert.assertTrue(itemService.getItem(checkItem).equals(insertItem));
    }

    @Test(dataProvider = "dataForUpdateItemTest")
    public void verifyUserCanUpdateItem(User user, Item firstItem, Item updateItem, Item checkItem){
        ItemService itemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .createItem(firstItem, false)
                .overrideItem(updateItem, true);
        Assert.assertTrue(itemService.getItem(checkItem).equals(updateItem));
    }



}

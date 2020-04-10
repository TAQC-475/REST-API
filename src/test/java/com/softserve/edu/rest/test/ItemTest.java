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
            return new Object[][]{{UserRepository.getValidUser(), ItemRepository.getDefaultItem(), ItemRepository.getDefaultItem()}};
    }

    @Test(dataProvider = "dataForItemTest")
    public void verifyUserCanCreateItem(User user, Item insertItem, Item checkItem){
        ItemService itemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(insertItem, false);
        Assert.assertTrue(itemService.getItem(checkItem).equals(insertItem));
    }
}

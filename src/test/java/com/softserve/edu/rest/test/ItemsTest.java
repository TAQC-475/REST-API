package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ItemsTest {
    @DataProvider
    public Object[][] dataForItemTest() {
        return new Object[][]{{UserRepository.getValidUser(), ItemRepository.getDefaultItem(), ItemRepository.getDefaultItem()}};
    }

    @Test(dataProvider = "dataForItemTest")
    public void verifyUserCanGetAllItems(User user, Item insertItem, Item checkItem) {
        System.out.println(new LoginService()
                .successfulUserLogin(user)
                .goToItemsService()
                .getAllItems());
    }
}

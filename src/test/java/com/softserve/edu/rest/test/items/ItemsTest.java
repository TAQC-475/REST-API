package com.softserve.edu.rest.test.items;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.DataForItemsTest;
import com.softserve.edu.rest.services.ItemsService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ItemsTest {
    @Test(dataProvider = "dataForGettingAllItemsTest", dataProviderClass = DataForItemsTest.class)
    public void verifyUserCanGetAllItems(User user, Item firstItem, Item secondItem, List<Item> testItemsList) {
        ItemsService itemsService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .createItem(firstItem, true)
                .createItem(secondItem, true)
                .goToItemsService();

        Assert.assertEquals(itemsService.getAllItemsList(), testItemsList, "Actual and expected user items lists are not equal");
    }

    @Test(dataProvider = "dataForAdminGettingUserItemsTest", dataProviderClass = DataForItemsTest.class)
    public void verifyAdminCanGetUserItems(User adminUser, User userToCheck) {
        String checkedUserItems = new LoginService()
                .successfulUserLogin(userToCheck)
                .goToItemsService()
                .getAllItems();

        String itemsGotByAdmin = new LoginService()
                .successfulAdminLogin(adminUser)
                .goToItemsService()
                .getAllUserItemsAsAdmin(userToCheck);

        Assert.assertEquals(checkedUserItems, itemsGotByAdmin, "Items got by user and user items got by admin are different");
    }

    @Test(dataProvider = "dataForVerifyingUserCantGetAdminItems", dataProviderClass = DataForItemsTest.class)
    public void verifyUserCantGetAdminItems(User adminUser, User userToCheck, Item firstItem) {
        String adminItems = new LoginService()
                .successfulAdminLogin(adminUser)
                .goToItemService()
                .createItem(firstItem, true)
                .goToItemsService()
                .getAllItems();

        String contentUserGetsTryingToGetAdminItems = new LoginService()
                .successfulUserLogin(userToCheck)
                .goToItemsService()
                .getAllUserItemsAsAdmin(userToCheck);

        Assert.assertTrue(adminItems.contains(firstItem.getItemText()) && contentUserGetsTryingToGetAdminItems.equals(""), "User can get admin items");
    }
}

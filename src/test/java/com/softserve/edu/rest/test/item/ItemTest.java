package com.softserve.edu.rest.test.item;


import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.dataproviders.ItemData;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.services.ItemService;
import com.softserve.edu.rest.services.LoginService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class ItemTest extends ItemTestRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemTest.class);

    /*
    Test 1
    Verify if User and Admin Can Create Item
    */
    @Parameters({"User", "Item"})
    @Test(dataProvider = "dataForAddItemTest", dataProviderClass = ItemData.class)
    public void verifyUserCanCreateItem(User user, Item item) {
        LOGGER.info("User = {} create new item = {}", user, item);
        String result = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(item, false)
                .getItem(item);
        Assert.assertEquals(result, item.getItemText());
        LOGGER.info("Item = {}, was added by User = {}", item, user);
    }

    /*
    Test 2
    Verify if User and Admin Can Update Item
    */
    @Parameters({"User", "Create new item", "Update new item"})
    @Test(dataProvider = "dataForUpdateItemTest", dataProviderClass = ItemData.class)
    public void verifyUserCanUpdateItem(User user, Item firstItem, Item updateItem) {
        LOGGER.info("User = {} crearte new item = {} , than update it = {}", user, firstItem, updateItem);
        String itemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(firstItem, false)
                .overwriteItem(firstItem, updateItem)
                .getItem(firstItem);
        Assert.assertEquals(itemService, updateItem.getItemText());
        LOGGER.info("Item = {} was created, then it was updated to = {}, by User = {}", firstItem, updateItem, user);
    }

    /*
    Test 3
    Verify if User and Admin Can Delete Item
    */
    @Parameters({"User", "Item"})
    @Test(dataProvider = "dataForDeleteItemTest", dataProviderClass = ItemData.class)
    public void verifyUserCanDeleteItem(User user, Item item) {
        LOGGER.info("User = {} delete item = {}", user, item);
        String result = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(item, false)
                .deleteItem(item)
                .getItem(item);
        Assert.assertEquals(result, null);
        LOGGER.info("User = {} deleted item = {}", user, item);
    }

    /*
    Test 4
    Verify If User Can Get Item Frome Another User and Admin
    */
    @Parameters({"User one", "User two", "Item of first user"})
    @Test(dataProvider = "dataForTwoUsersTest", dataProviderClass = ItemData.class)
    public void verifyIfUserCanNotSeeItemFromeAnotherUser(User user1, User user2, Item itemUserOne) {
        LOGGER.info("User = {} create new item = {}, User = {} trying to get item ={} from User ={}", user1, itemUserOne, user2, itemUserOne, user1);
        ItemService userOne = new LoginService()
                .successfulUserLogin(user1)
                .goToItemService()
                .addItem(itemUserOne, false);
        String result = new LoginService()
                .successfulUserLogin(user2)
                .goToItemService()
                .getAnotherUserIterm(user1, itemUserOne);
        Assert.assertNotEquals(result, itemUserOne.getItemText());
        LOGGER.info("User = {} didn't get item = {}, from User = {}", user2, itemUserOne, user1);
    }

    /*
        Test 5
        Verify If Admin Can Get Item Frome Another User
    */
    @Parameters({"User", "Admin", "User item"})
    @Test(dataProvider = "dataForAdminAndUserTest", dataProviderClass = ItemData.class)
    public void verifyIfAdminCanSeeUsersItem(User user, User admin, Item userItem) {
        LOGGER.info("User = {} create new item = {}, Admin = {} trying to get item ={} from User ={}", user, userItem, admin, userItem, user);
        ItemService userItemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(userItem, false);
        String result = new LoginService()
                .successfulAdminLogin(admin)
                .goToItemService()
                .getAnotherUserIterm(user, userItem);
        Assert.assertEquals(result, userItem.getItemText());
        LOGGER.info("Admin = {} got item = {}, from User = {}", admin, userItem, user);
    }

}

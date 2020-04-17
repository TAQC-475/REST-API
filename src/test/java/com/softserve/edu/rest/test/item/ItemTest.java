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

    /**
     * logs in as user/admin, adds an item and verifies that user can get his item
     * @param user valid user/admin
     * @param item item to add
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

    /**
     * logs in as user/admin, adds an item and verifies that user can overwrite his item
     * @param user valid user/admin
     * @param initialItem the item that the user adds first
     * @param updateItem the item which overwrites the initialItem
     */
    @Parameters({"User", "Create new item", "Update new item"})
    @Test(dataProvider = "dataForUpdateItemTest", dataProviderClass = ItemData.class)
    public void verifyUserCanUpdateItem(User user, Item initialItem, Item updateItem) {
        LOGGER.info("User = {} crearte new item = {} , than update it = {}", user, initialItem, updateItem);
        String itemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(initialItem, false)
                .overwriteItem(initialItem, updateItem)
                .getItem(initialItem);
        Assert.assertEquals(itemService, updateItem.getItemText());
        LOGGER.info("Item = {} was created, then it was updated to = {}, by User = {}", initialItem, updateItem, user);
    }

    /**
     * logs in as user/admin, adds an item and verifies that user can delete that item
     * @param user valid user/admin
     * @param item item to add and after that delete it
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

    /**
     * logs in as user/admin, adds an item
     * then logs as another user
     * and try to get the item which was added by user/admin in the step before
     * then verifies that user can't get that item
     * @param userOne valid user/admin which adds item
     * @param userTwo the user who tries to get not his item
     * @param itemUserOne item to add
     */
    @Parameters({"User one", "User two", "Item of first user"})
    @Test(dataProvider = "dataForTwoUsersTest", dataProviderClass = ItemData.class)
    public void verifyIfUserCanNotSeeItemFromeAnotherUser(User userOne, User userTwo, Item itemUserOne) {
        LOGGER.info("User = {} create new item = {}, User = {} trying to get item ={} from User ={}", userOne, itemUserOne, userTwo, itemUserOne, userOne);
        ItemService user = new LoginService()
                .successfulUserLogin(userOne)
                .goToItemService()
                .addItem(itemUserOne, false);
        String result = new LoginService()
                .successfulUserLogin(userTwo)
                .goToItemService()
                .getAnotherUserItem(userOne, itemUserOne);
        Assert.assertNotEquals(result, itemUserOne.getItemText());
        LOGGER.info("User = {} didn't get item = {}, from User = {}", userTwo, itemUserOne, userOne);
    }

    /**
     * logs in as user, adds an item
     * then logs as admin
     * and try to get the item which was added by user in the step before
     * then verifies that admin can get user item
     * @param user valid user which adds item
     * @param admin admin who tries to get the user item
     * @param userItem item to add
     */
    @Parameters({"User", "Admin", "User item"})
    @Test(dataProvider = "dataForAdminAndUserTest", dataProviderClass = ItemData.class)
    public void verifyIfAdminCanSeeUserItem(User user, User admin, Item userItem) {
        LOGGER.info("User = {} create new item = {}, Admin = {} trying to get item ={} from User ={}", user, userItem, admin, userItem, user);
        ItemService userItemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(userItem, false);
        String result = new LoginService()
                .successfulAdminLogin(admin)
                .goToItemService()
                .getAnotherUserItem(user, userItem);
        Assert.assertEquals(result, userItem.getItemText());
        LOGGER.info("Admin = {} got item = {}, from User = {}", admin, userItem, user);
    }

}

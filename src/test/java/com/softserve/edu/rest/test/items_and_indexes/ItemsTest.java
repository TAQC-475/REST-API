package com.softserve.edu.rest.test.items_and_indexes;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.DataForItemsTest;
import com.softserve.edu.rest.services.ItemsService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.test.RestTestRunner;
import io.qameta.allure.Epic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Test of items resource")
public class ItemsTest extends RestTestRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsTest.class);

    /**
     * logs in as user, adds two items and verifies that user can get this items
     * @param user user
     * @param firstItem item to add
     * @param secondItem item to add
     * @param testItemsList list of added items
     */
    @Parameters({"Existing user", "First item to add", "Second item to add", "List of added items"})
    @Test(dataProvider = "dataForGettingAllItemsTest", dataProviderClass = DataForItemsTest.class)
    public void verifyUserCanGetAllItems(User user, Item firstItem, Item secondItem, List<Item> testItemsList) {
        LOGGER.info("Logging in as user = {} and adding two items = {} {} ", user.getName(), firstItem, secondItem);
        ItemsService itemsService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(firstItem, true)
                .addItem(secondItem, true)
                .goToItemsService();

        Assert.assertEquals(itemsService.getAllItemsList(), testItemsList, "Actual and expected user items lists are not equal");
        LOGGER.info("Items = {} were added", itemsService.getAllItemsList());
    }

    /**
     * logs in as user from param, adds item and gets his items,
     * then logs in as admin and gets all items of user from param,
     * verifies item got by user and by admin are equal
     * @param adminUser admin
     * @param userToCheck user
     * @param firstItem item to add
     */
    @Parameters({"Admin user", "User to check"})
    @Test(dataProvider = "dataForAdminGettingUserItemsTest", dataProviderClass = DataForItemsTest.class)
    public void verifyAdminCanGetUserItems(User adminUser, User userToCheck, Item firstItem, Item secondItem) {
        LOGGER.info("Logging in and getting all user items as admin [" + adminUser.getName() + "] and as user [" +userToCheck.getName() + "] , asserting that result is equal");
        String checkedUserItems = new LoginService()
                .successfulUserLogin(userToCheck)
                .goToItemService()
                .addItem(firstItem, true)
                .addItem(secondItem, true)
                .goToItemsService()
                .getAllItems();

        String itemsGotByAdmin = new LoginService()
                .successfulAdminLogin(adminUser)
                .goToItemsService()
                .getAllUserItemsAsAdmin(userToCheck);

        Assert.assertEquals(checkedUserItems, itemsGotByAdmin, "Items got by user and user items got by admin are different");
        LOGGER.info("Items got by user = {} items got by admin = {}", checkedUserItems, itemsGotByAdmin);
    }

    /**
     * logs as admin, ads item and get his items
     * then logs as user and tries to get admin items
     * then verifies that user can't get admin items
     * @param adminUser admin
     * @param userToCheck user
     * @param firstItem item to add
     */
    @Parameters({"Admin user", "User", "Item added by admin"})
    @Test(dataProvider = "dataForVerifyingUserCantGetAdminItems", dataProviderClass = DataForItemsTest.class)
    public void verifyUserCantGetAdminItems(User adminUser, User userToCheck, Item firstItem, Item secondItem) {
        LOGGER.info("Logging in as admin = {} adding item {} then logging in as user {} and trying to get admin items", adminUser.getName(), firstItem.toString(), userToCheck.getName());
        String adminItems = new LoginService()
                .successfulAdminLogin(adminUser)
                .goToItemService()
                .addItem(firstItem, true)
                .addItem(secondItem, true)
                .goToItemsService()
                .getAllItems();

        String contentUserGetsTryingToGetAdminItems = new LoginService()
                .successfulUserLogin(userToCheck)
                .goToItemsService()
                .getAllUserItemsAsAdmin(adminUser);

        Assert.assertTrue(adminItems.contains(firstItem.getItemText()) && contentUserGetsTryingToGetAdminItems.equals(""), "User can get admin items");
        LOGGER.info("User gets: " + contentUserGetsTryingToGetAdminItems);
    }
}

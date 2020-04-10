package com.softserve.edu.rest.test.items;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.DataForItemsTest;
import com.softserve.edu.rest.services.ItemsService;
import com.softserve.edu.rest.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class ItemsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsTest.class);

    @Parameters({"Existing user", "First item to add", "Second item to add", "List of added items"})
    @Test(dataProvider = "dataForGettingAllItemsTest", dataProviderClass = DataForItemsTest.class)
    public void verifyUserCanGetAllItems(User user, Item firstItem, Item secondItem, List<Item> testItemsList) {
        LOGGER.info("Logging in as user [" + user.getName() + "] and adding two items: " + firstItem.toString() + " " + secondItem.toString());
        ItemsService itemsService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .createItem(firstItem, true)
                .createItem(secondItem, true)
                .goToItemsService();

        Assert.assertEquals(itemsService.getAllItemsList(), testItemsList, "Actual and expected user items lists are not equal");
        LOGGER.info("Items: " + testItemsList.toString() + " were added");
    }

    @Parameters({"Admin user", "User to check"})
    @Test(dataProvider = "dataForAdminGettingUserItemsTest", dataProviderClass = DataForItemsTest.class)
    public void verifyAdminCanGetUserItems(User adminUser, User userToCheck) {
        LOGGER.info("Logging in and getting all user items as admin [" + adminUser.getName() + "] and as user [" +userToCheck.getName() + "] , asserting that result is equal");
        String checkedUserItems = new LoginService()
                .successfulUserLogin(userToCheck)
                .goToItemsService()
                .getAllItems();

        String itemsGotByAdmin = new LoginService()
                .successfulAdminLogin(adminUser)
                .goToItemsService()
                .getAllUserItemsAsAdmin(userToCheck);

        Assert.assertEquals(checkedUserItems, itemsGotByAdmin, "Items got by user and user items got by admin are different");
        LOGGER.info("Items got by user " + checkedUserItems + " items got by admin " + itemsGotByAdmin);
    }

    @Parameters({"Admin user", "User", "Item added by admin"})
    @Test(dataProvider = "dataForVerifyingUserCantGetAdminItems", dataProviderClass = DataForItemsTest.class)
    public void verifyUserCantGetAdminItems(User adminUser, User userToCheck, Item firstItem) {
        LOGGER.info("Logging in as admin " + adminUser.getName() + " adding item [" + firstItem.toString() + "], then logging in as user " +
                userToCheck.getName() + " and trying to get admin items");
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
        LOGGER.info("User gets: " + contentUserGetsTryingToGetAdminItems);
    }
}

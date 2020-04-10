package com.softserve.edu.rest.test.items;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.DataForIndexTest;
import com.softserve.edu.rest.data.dataproviders.DataForItemsTest;
import com.softserve.edu.rest.services.ItemsService;
import com.softserve.edu.rest.services.LoginService;
import io.qameta.allure.Epic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Test indexes")
public class IndexTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexTest.class);

    @Parameters({"Existing user", "Item with valid index"})
    @Test(dataProvider = "dataForCreatingItemWithValidIndexValue", dataProviderClass = DataForIndexTest.class)
    public void verifyCreatingItemWithValidIndex(User user, Item itemWithValidIndex) {
        LOGGER.info("adding item with valid index: " + itemWithValidIndex.getItemIndex());
        ItemsService itemsService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(itemWithValidIndex, true)
                .goToItemsService();

        Assert.assertTrue(itemsService.getAllItemsList().contains(itemWithValidIndex), "Item: " + itemWithValidIndex.getItemText() + " was not added");
        LOGGER.info("item with valid index [" + itemWithValidIndex.getItemIndex() + "] added");
    }

    @Parameters({"Existing user", "Item with invalid index"})
    @Test(dataProvider = "dataForCreatingItemWithInvalidIndexValue", dataProviderClass = DataForIndexTest.class)
    public void verifyItemCantBeCreatedWithInvalidIndex(User user, Item itemWithInvalidIndex) {
        LOGGER.info("adding item with invalid index: " + itemWithInvalidIndex.getItemIndex());
        String statusCode = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .getCreateItemRequestStatusCode(itemWithInvalidIndex, true);

        Assert.assertEquals(statusCode, "400", itemWithInvalidIndex.toString() + " with invalid index was added");
        LOGGER.info("item with invalid index [" + itemWithInvalidIndex.getItemIndex() + "] added");
    }

    @Parameters({"Existing user", "First item to add", "Second item to add", "Indexes list of added items"})
    @Test(dataProvider = "dataForVerifyingUserCanGetAllItemsIndexes", dataProviderClass = DataForItemsTest.class)
    public void verifyUserCanGetAllItemsIndexes(User user, Item firstItem, Item secondItem, List<String> testItemsIndexes) {
        List<String> itemsIndexes = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(firstItem, true)
                .addItem(secondItem, true)
                .goToItemsIndexesService()
                .getAllItemsIndexes();

        Assert.assertEquals(itemsIndexes, testItemsIndexes, "Actual and expected items indexes are not equal");
    }
}

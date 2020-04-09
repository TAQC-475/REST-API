package com.softserve.edu.rest.test.items;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.DataForIndexTest;
import com.softserve.edu.rest.services.ItemsService;
import com.softserve.edu.rest.services.LoginService;
import io.qameta.allure.Epic;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.slf4j.Logger;

@Epic("Test indexes")
public class IndexTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexTest.class);

    @Test(dataProvider = "dataForCreatingItemWithValidIndexValue", dataProviderClass = DataForIndexTest.class,
    description = "verifyCreatingItemWIthValidIndex")
    public void verifyCreatingItemWIthValidIndex(User user, Item itemWithValidIndex) {
        LOGGER.info("adding item with valid index: " + itemWithValidIndex.getItemIndex());
        ItemsService itemsService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .createItem(itemWithValidIndex, true)
                .goToItemsService();

        Assert.assertTrue(itemsService.getAllItemsList().contains(itemWithValidIndex));
        LOGGER.info("item with valid index [" + itemWithValidIndex.getItemIndex() + "] added");
    }

    @Test(dataProvider = "dataForCreatingItemWithInvalidIndexValue", dataProviderClass = DataForIndexTest.class,
    description = "verifyItemCantBeCreatedWithInvalidIndex")
    public void verifyItemCantBeCreatedWithInvalidIndex(User user, Item itemWithInvalidIndex) {
        LOGGER.info("adding item with invalid index: " + itemWithInvalidIndex.getItemIndex());
        String statusCode = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .getCreateItemRequestStatusCode(itemWithInvalidIndex, true);

        Assert.assertEquals(statusCode, "400", itemWithInvalidIndex.toString() + " with invalid index was added");
        LOGGER.info("item with invalid index [" + itemWithInvalidIndex.getItemIndex() + "] added");
    }
}

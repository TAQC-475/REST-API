package com.softserve.edu.rest.test.items_and_indexes;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.DataForIndexTest;
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

import static org.seleniumhq.jetty9.http.HttpStatus.Code.BAD_REQUEST;

@Epic("Test indexes")
public class IndexTest extends RestTestRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexTest.class);

    /**
     * logs in as user and tries to add item with valid index
     * verifies that user can add item with valid index
     * @param user user
     * @param itemWithValidIndex item with valid index
     */
    @Parameters({"Existing user", "Item with valid index"})
    @Test(dataProvider = "dataForCreatingItemWithValidIndexValue", dataProviderClass = DataForIndexTest.class)
    public void verifyAddingItemWithValidIndex(User user, Item itemWithValidIndex) {
        LOGGER.info("adding item with valid index: " + itemWithValidIndex.getItemIndex());
        ItemsService itemsService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(itemWithValidIndex, true)
                .goToItemsService();

        Assert.assertTrue(itemsService.getAllItemsList().contains(itemWithValidIndex), "Item: " + itemWithValidIndex.getItemText() + " was not added");
        LOGGER.info("item with valid index [" + itemWithValidIndex.getItemIndex() + "] added");
    }

    /**
     * logs in as user and tries to add item with invalid index
     * verifies that user can't add item with invalid index
     * @param user user
     * @param itemWithInvalidIndex item with invalid index
     */
    @Parameters({"Existing user", "Item with invalid index"})
    @Test(dataProvider = "dataForCreatingItemWithInvalidIndexValue", dataProviderClass = DataForIndexTest.class)
    public void verifyItemWithInvalidIndexCantBeAdded(User user, Item itemWithInvalidIndex) {
        LOGGER.info("adding item with invalid index: " + itemWithInvalidIndex.getItemIndex());
        String statusCode = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .getCreateItemRequestStatusCode(itemWithInvalidIndex, true);

        Assert.assertEquals(Integer.parseInt(statusCode), BAD_REQUEST.getCode(), itemWithInvalidIndex.toString() + " with invalid index was added");
        LOGGER.info("Adding item with invalid index = {} status code = {}", itemWithInvalidIndex.getItemIndex(), statusCode);
    }

    /**
     * logs in as user, adds two items and gets all his items indexes
     * verifies that user can get indexes of all his items
     * @param user user
     * @param firstItem item to add
     * @param secondItem item to add
     * @param testItemsIndexes indexes of added items
     */
    @Parameters({"Existing user", "First item to add", "Second item to add", "Indexes list of added items"})
    @Test(dataProvider = "dataForVerifyingUserCanGetAllItemsIndexes", dataProviderClass = DataForIndexTest.class)
    public void verifyUserCanGetAllItemsIndexes(User user, Item firstItem, Item secondItem, List<String> testItemsIndexes) {
        LOGGER.info("Adding items with indexes = {} {} and verifying user can get those indexes", firstItem.getItemIndex(), secondItem.getItemIndex());
        List<String> itemsIndexes = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(firstItem, true)
                .addItem(secondItem, true)
                .goToItemsIndexesService()
                .getAllItemsIndexes();

        Assert.assertEquals(itemsIndexes, testItemsIndexes, "Actual and expected items indexes are not equal");
        LOGGER.info("Indexes got by user = {}", itemsIndexes);
    }
}

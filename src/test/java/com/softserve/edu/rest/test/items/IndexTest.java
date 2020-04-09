package com.softserve.edu.rest.test.items;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.DataForIndexTest;
import com.softserve.edu.rest.services.ItemsService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IndexTest {
    @Test(dataProvider = "dataForCreatingItemWithValidIndexValue", dataProviderClass = DataForIndexTest.class)
    public void verifyCreatingItemWIthValidIndex(User user, Item itemWithValidIndex) {
        ItemsService itemsService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .createItem(itemWithValidIndex, true)
                .goToItemsService();

        Assert.assertTrue(itemsService.getAllItemsList().contains(itemWithValidIndex));
    }

    @Test(dataProvider = "dataForCreatingItemWithInvalidIndexValue", dataProviderClass = DataForIndexTest.class)
    public void verifyItemCantBeCreatedWithInvalidIndex(User user, Item itemWithInvalidIndex) {
        String statusCode = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .getCreateItemRequestStatusCode(itemWithInvalidIndex, true);

        Assert.assertEquals(statusCode, "400", itemWithInvalidIndex.toString() + " with invalid index was added");
    }
}

package com.softserve.edu.rest.test.items;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.ItemsService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IndexTest {
    @DataProvider
    public Object[][] dataForCreatingItemWithValidIndexValue() {
        return new Object[][]{{UserRepository.getValidUser(), ItemRepository.getItemWithMinIndex()},
                {UserRepository.getValidUser(), ItemRepository.getItemWithIndexBetweenMinAndMax()},
                {UserRepository.getValidUser(), ItemRepository.getItemWithMaxIndex()}};
    }

    @DataProvider
    public Object[][] dataForCreatingItemWithInvalidIndexValue() {
        return new Object[][]{{UserRepository.getValidUser(), ItemRepository.getItemWithLetterIndex()},
                {UserRepository.getValidUser(), ItemRepository.getItemWithNegativeIndex()},
                {UserRepository.getValidUser(), ItemRepository.getItemWithIndexHigherThanMax()},
                {UserRepository.getValidUser(), ItemRepository.getItemWithSpecialCharacterIndex()}};
    }

    @Test(dataProvider = "dataForCreatingItemWithValidIndexValue")
    public void verifyCreatingItemWIthValidIndex(User user, Item itemWithValidIndex) {
        ItemsService itemsService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .createItem(itemWithValidIndex, true)
                .goToItemsService();

        Assert.assertTrue(itemsService.getAllItemsList().contains(itemWithValidIndex));
    }

    @Test(dataProvider = "dataForCreatingItemWithInvalidIndexValue")
    public void verifyItemCantBeCreatedWithInvalidIndex(User user, Item itemWithInvalidIndex) {
        String statusCode = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .getCreateItemRequestStatusCode(itemWithInvalidIndex, true);

        Assert.assertEquals(statusCode, "400", itemWithInvalidIndex.toString() + " with invalid index was added");
    }
}

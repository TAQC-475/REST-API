package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ItemsTest {
    @DataProvider
    public Object[][] dataForGettingAllItemsTest() {
        return new Object[][]{{UserRepository.getValidUser(), ItemRepository.getCoreI5(), ItemRepository.getCoreI7()}};
    }

    @DataProvider
    public Object[][] dataForAdminGettingUserItemsTest() {
        return new Object[][]{{UserRepository.getAdmin(), UserRepository.getValidUser()}};
    }

    @Test(dataProvider = "dataForGettingAllItemsTest")
    public void verifyUserCanGetAllItems(User user, Item firstItemToAdd, Item secondItemToAdd) {
        Assert.assertTrue(!new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .createItem(firstItemToAdd, true)
                .createItem(secondItemToAdd, true)
                .goToItemsService()
                .getAllItems().isEmpty());
    }

    @Test(dataProvider = "dataForAdminGettingUserItemsTest")
    public void verifyAdminCanGetUserItems(User adminUser, User userToCheck){
        String checkedUserItems = new LoginService()
                .successfulUserLogin(userToCheck)
                .goToItemsService()
                .getAllItems();

        String itemsGotByAdmin = new LoginService()
                .successfulAdminLogin(adminUser)
                .goToItemsService()
                .getAllUserItemsAsAdmin(userToCheck);

        Assert.assertEquals(checkedUserItems, itemsGotByAdmin);
    }
}

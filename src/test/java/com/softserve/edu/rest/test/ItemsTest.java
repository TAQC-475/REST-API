package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.ItemService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ItemsTest {
    @DataProvider
    public Object[][] dataForGettingAllItemsTest() {
        return new Object[][]{{UserRepository.getValidUser(), ItemRepository.getCoreI5(), ItemRepository.getCoreI7(), ItemRepository.getCoreI9()}};
    }

    @DataProvider
    public Object[][] dataForAdminGettingUserItemsTest() {
        return new Object[][]{{UserRepository.getAdmin(), UserRepository.getValidUser()}};
    }

    @DataProvider
    public Object[][] dataForVerifyingUserCantGetAdminItems() {
        return new Object[][]{{UserRepository.getAdmin(), UserRepository.getValidUser(), ItemRepository.getCoreI7(), ItemRepository.getCoreI9()}};
    }

    @Test(dataProvider = "dataForGettingAllItemsTest")
    public void verifyUserCanGetAllItems(User user, Item firstItem, Item secondItem, Item thirdItem) {
//        Assert.assertTrue(!new LoginService()
//                .successfulUserLogin(user)
//                .goToItemService()
//                .createItem(firstItemToAdd, true)
//                .createItem(secondItemToAdd, true)
//                .goToItemsService()
//                .getAllItems().isEmpty());

        ItemService itemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .createItem(firstItem, true);

        String[] initialItems = itemService.goToItemsService().getAllItems().split("\n");
        int initialNumberOfItems = initialItems.length;

        String[] itemsAfterAddingTwoItems = itemService.createItem(secondItem, true)
                .createItem(thirdItem, true)
                .goToItemsService()
                .getAllItems().split("\n");
        int numberOfItemsAfterAddingTwoItems = itemsAfterAddingTwoItems.length;

        Assert.assertTrue(numberOfItemsAfterAddingTwoItems - initialNumberOfItems == 2);
    }

    @Test(dataProvider = "dataForAdminGettingUserItemsTest")
    public void verifyAdminCanGetUserItems(User adminUser, User userToCheck) {
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

    @Test(dataProvider = "dataForVerifyingUserCantGetAdminItems")
    public void verifyUserCantGetAdminItems(User adminUser, User userToCheck, Item firstItem, Item secondItem){
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

        Assert.assertTrue(adminItems.contains(firstItem.getItemText()) && contentUserGetsTryingToGetAdminItems.equals(""));
    }
}

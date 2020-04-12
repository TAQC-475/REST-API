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

import java.sql.SQLOutput;

public class ItemTest {
    @DataProvider
    public Object[][] dataForAddItemTest(){
            return new Object[][]{{UserRepository.getValidUser(),
                    ItemRepository.getDefaultItem(), ItemRepository.getDefaultItem()}};
    }
    @DataProvider
    public Object[][] dataForUpdateItemTest(){
        return new Object[][]{{UserRepository.getAkimatcUser(),
                ItemRepository.getCoreI5(), ItemRepository.getCoreI7(), ItemRepository.getCoreI7()}};
    }
    @DataProvider
    public Object[][] dataForDeleteItemTest() {
        return new Object[][]{{UserRepository.getAkimatcUser(),
                ItemRepository.getCoreI9()}};
    }
    @DataProvider
    public Object[][] dataForAddItemByAdminTest(){
        return new Object[][]{{UserRepository.getAdmin(),
                ItemRepository.getCoreI5(), ItemRepository.getCoreI5()}};
    }
    @DataProvider
    public Object[][] dataForTwoUsersTest(){
        return new Object[][]{{UserRepository.getUserDana(), UserRepository.getValidUser(),
                ItemRepository.getCoreI5(), ItemRepository.getCoreI5()}};
    }
    @DataProvider
    public Object[][] dataForAdminAndUserTest(){
        return new Object[][]{{UserRepository.getAdmin(), UserRepository.getValidUser(),
                ItemRepository.getCoreI7(), ItemRepository.getCoreI7()}};
    }
    @DataProvider
    public Object[][] dataForAdminAndNewUserTest(){
        return new Object[][]{{UserRepository.getAdmin(), UserRepository.getValidUser(),
                ItemRepository.getCoreI7(), ItemRepository.getCoreI7()}};
    }

    /*
    Test 1
    Verify if User Can Create Item

    */
    @Test(dataProvider = "dataForAddItemTest")
    public void verifyUserCanCreateItem(User user, Item insertItem, Item checkItem){
        ItemService itemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(insertItem, false);
        Assert.assertTrue(itemService.getItem(checkItem).equals(insertItem));
    }

    /*
    Test 2
    Verify if User Can Update Item
    */
    @Test(dataProvider = "dataForUpdateItemTest")
    public void verifyUserCanUpdateItem(User user, Item firstItem, Item updateItem, Item checkItem){
        ItemService itemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(firstItem, false)
                .overrideItem(updateItem);
        Assert.assertTrue(itemService.getItem(checkItem).equals(updateItem));
    }

    /*
    Test 3
    Verify if User Can Delete Item
    */
    @Test(dataProvider = "dataForDeleteItemTest")
    public void verifyUserCanDeleteItem(User user, Item insertItem){
        ItemService itemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(insertItem, false)
                .deleteItem(insertItem);
               // .getItem(insertItem);
       Assert.assertEquals(itemService, null);
    }

    /*
        Test 4
        Verify If Admin Can Add Item
    */

    @Test(dataProvider = "dataForAddItemByAdminTest")
    public void verifyIfAdminCanAddItem(User admin, Item insertItem, Item checkItem){
        ItemService adminsItem = new LoginService()
                .successfulAdminLogin(admin)
                .goToItemService()
                .addItem(insertItem, false);
        Assert.assertTrue(adminsItem.getItem(checkItem).equals(insertItem));
    }
        /*
        Test 5
        Verify If User Can Get Item Frome Another User
        */
    @Test(dataProvider = "dataForTwoUsersTest")
    public void verifyIfUserCanSeeItemFromeAnotherUser(User user1, User user2, Item itemUserOne, Item checkItem){
        ItemService userOne = new LoginService()
                .successfulUserLogin(user1)
                .goToItemService()
                .addItem(itemUserOne, false);
        Item  userTwo = new LoginService()
                .successfulUserLogin(user2)
                .goToItemService()
                .getUserItemByAnotherUser(user1, itemUserOne);;
        Assert.assertNotEquals(userTwo, (checkItem));
    }

    /*
        Test 6
        Verify If Admin Can Get Item Frome Another User
    */
    @Test(dataProvider = "dataForAdminAndUserTest")
    public void verifyIfAdminCanSeeUsersItem(User admin, User user, Item insertItem, Item checkItem){
        ItemService userItemService = new LoginService()
                .successfulUserLogin(user)
                .goToItemService()
                .addItem(insertItem, false);
        Item adminItemService = new LoginService()
                .successfulAdminLogin(admin)
                .goToItemService()
                .getUserItemByAnotherUser(user, insertItem);
        Assert.assertEquals(adminItemService, checkItem);
    }

}

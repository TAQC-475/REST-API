package com.softserve.edu.rest.test.item;


import com.softserve.edu.rest.data.Item;
import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.dataproviders.ItemData;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.ItemService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ItemTest {
    /*
    Test 1
    Verify if User Can Create Item
    */
    @Test(dataProvider = "dataForAddItemTest", dataProviderClass=ItemData.class)
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
    @Test(dataProvider = "dataForUpdateItemTest", dataProviderClass=ItemData.class)
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
    @Test(dataProvider = "dataForDeleteItemTest", dataProviderClass=ItemData.class)
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

    @Test(dataProvider = "dataForAddItemByAdminTest", dataProviderClass=ItemData.class)
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
    @Test(dataProvider = "dataForTwoUsersTest", dataProviderClass=ItemData.class)
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
    @Test(dataProvider = "dataForAdminAndUserTest", dataProviderClass=ItemData.class)
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

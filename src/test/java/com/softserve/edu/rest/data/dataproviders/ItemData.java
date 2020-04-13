package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class ItemData {
    @DataProvider
    public Object[][] dataForAddItemTest(){
        return new Object[][]{{UserRepository.getValidUser(),
                ItemRepository.getDefaultItem(), ItemRepository.getDefaultItem()}};
    }
    @DataProvider
    public Object[][] dataForUpdateItemTest(){
        return new Object[][]{
                {UserRepository.getAdmin(),
                        ItemRepository.getCoreI5(), ItemRepository.getCoreI7(), ItemRepository.getCoreI7()},
                {UserRepository.getUserVasya(),
                        ItemRepository.getCoreI5(), ItemRepository.getCoreI7(), ItemRepository.getCoreI7()},
                {UserRepository.getAdminDana(),
                        ItemRepository.getCoreI5(), ItemRepository.getCoreI7(), ItemRepository.getCoreI7()}
        };
    }
    @DataProvider
    public Object[][] dataForDeleteItemTest() {
        return new Object[][]{{UserRepository.getAkimatcUser(),
                ItemRepository.getCoreI9()}};
    }
    @DataProvider
    public Object[][] dataForAddItemByAdminTest(){
        return new Object[][]{{UserRepository.getAdmin(),
                ItemRepository.getCoreI9(), ItemRepository.getCoreI9()}};
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
}

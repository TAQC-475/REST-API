package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class DataForItemsTest {
    @DataProvider
    public Object[][] dataForGettingAllItemsTest() {
        return new Object[][]{{UserRepository.getValidUser(), ItemRepository.getCoreI5(), ItemRepository.getCoreI7(), ItemRepository.getTestItemsList()}};
    }

    @DataProvider
    public Object[][] dataForAdminGettingUserItemsTest() {
        return new Object[][]{{UserRepository.getAdmin(), UserRepository.getValidUser(), ItemRepository.getCoreI5(), ItemRepository.getCoreI7()}};
    }

    @DataProvider
    public Object[][] dataForVerifyingUserCantGetAdminItems() {
        return new Object[][]{{UserRepository.getAdmin(), UserRepository.getValidUser(), ItemRepository.getCoreI5(), ItemRepository.getCoreI7()}};
    }
}

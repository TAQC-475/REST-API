package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class ItemData {
    @DataProvider
    public Object[][] dataForAddItemTest() {
        return new Object[][]{
                {UserRepository.getValidUser(), ItemRepository.getDefaultItem()},
                {UserRepository.getAdmin(), ItemRepository.getDefaultItem()}};
    }

    @DataProvider
    public Object[][] dataForUpdateItemTest() {
        return new Object[][]{
                {UserRepository.getAdmin(), ItemRepository.getCoreI5(), ItemRepository.getCoreI7()},
                {UserRepository.getValidUser(), ItemRepository.getCoreI5(), ItemRepository.getCoreI7()}};
    }

    @DataProvider
    public Object[][] dataForDeleteItemTest() {
        return new Object[][]{
                {UserRepository.getAdmin(), ItemRepository.getCoreI7()},
                {UserRepository.getAkimatcUser(), ItemRepository.getCoreI7()}};
    }


    @DataProvider
    public Object[][] dataForTwoUsersTest() {
        return new Object[][]{
                {UserRepository.getUserDana(), UserRepository.getValidUser(), ItemRepository.getCoreI9()},
                {UserRepository.getAdmin(), UserRepository.getValidUser(), ItemRepository.getCoreI9()}
        };
    }

    @DataProvider
    public Object[][] dataForAdminAndUserTest() {
        return new Object[][]{
                {UserRepository.getValidUser(), UserRepository.getAdmin(), ItemRepository.getCoreI9()}};
    }

}

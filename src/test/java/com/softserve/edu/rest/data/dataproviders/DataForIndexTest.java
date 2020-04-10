package com.softserve.edu.rest.data.dataproviders;

import com.softserve.edu.rest.data.ItemRepository;
import com.softserve.edu.rest.data.UserRepository;
import org.testng.annotations.DataProvider;

public class DataForIndexTest {
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
}

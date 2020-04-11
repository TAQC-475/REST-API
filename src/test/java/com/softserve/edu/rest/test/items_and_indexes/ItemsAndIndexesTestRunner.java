package com.softserve.edu.rest.test.items_and_indexes;

import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

public class ItemsAndIndexesTestRunner extends RestTestRunner {
    @Override
    @AfterMethod
    public void afterMethod(ITestResult result){
        new GuestService().resetServiceToInitialState();
        if (!result.isSuccess()) {
            LOGGER.warn("Test fail: " + result.getName());
        }
    }
}

package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class UserTestRunner extends RestTestRunner {
    protected SoftAssert softAssert = new SoftAssert();


    @Override
    @AfterMethod
    public void afterMethod(ITestResult result){
        new GuestService().resetServiceToInitialState();
        if (!result.isSuccess()) {
            LOGGER.warn("Test fail: " + result.getName());
            System.out.println("***Test " + result.getName() + " ERROR");
        }
    }
}

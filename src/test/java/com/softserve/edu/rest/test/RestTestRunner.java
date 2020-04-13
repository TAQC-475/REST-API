package com.softserve.edu.rest.test;

import com.softserve.edu.rest.services.GuestService;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.Map;

public abstract class RestTestRunner {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final Long ONE_SECOND_DELAY = 1000L;

    @Step
    @BeforeClass
    public void beforeClass(ITestContext context) {
        for (Map.Entry<String, String> entry : context.getCurrentXmlTest().getAllParameters().entrySet()) {
            System.out.println("Key: " + entry.getKey() + "  Value: " + entry.getValue());
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        loadApplication().resetServiceToInitialState();
        if (!result.isSuccess()) {
            LOGGER.warn("Test fail: " + result.getName());
            System.out.println("***Test " + result.getName() + " ERROR");
        }
    }

    @Step("Load_Application")
    public GuestService loadApplication() {
        return new GuestService();
    }

    public void presentationSleep() {
        presentationSleep(1);
    }

    public void presentationSleep(Integer seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY); // For Presentation ONLY
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

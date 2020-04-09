package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.services.GuestService;
import org.testng.annotations.AfterMethod;

public class UserTestRunner {

    @AfterMethod
    public void afterMethod() {
        new GuestService().resetServiceToInitialState();
    }
}

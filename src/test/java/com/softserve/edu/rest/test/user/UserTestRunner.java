package com.softserve.edu.rest.test.user;

import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.annotations.AfterMethod;

public class UserTestRunner  {
    GuestService guestService;

    @AfterMethod
    public void afterMethod(){
        guestService.resetServiceToInitialState();
    }
}

package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.services.GuestService;
import org.testng.annotations.AfterMethod;

public abstract class LoginTestRunner {
    protected final short tokenLength = 32;
    @AfterMethod()
    public void removeCreated() {
        new GuestService().resetServiceToInitialState();
    }


}

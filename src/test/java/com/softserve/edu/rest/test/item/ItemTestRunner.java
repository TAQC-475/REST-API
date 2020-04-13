package com.softserve.edu.rest.test.item;

import com.softserve.edu.rest.services.GuestService;
import org.testng.annotations.AfterMethod;

public abstract class ItemTestRunner {
    @AfterMethod()
    public void removeCreated() {
        new GuestService().resetServiceToInitialState();
    }
}

package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginUserNegativeTest extends RestTestRunner {
    @Test(dataProviderClass = UsersTestData.class, dataProvider = "nonExistingUserDataProvider")
    public void loginUserNegativeTest(User admin, User notExisingUser){

    }
}

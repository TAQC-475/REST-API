package com.softserve.edu.rest.test;

import com.softserve.edu.rest.services.AdminServiceDoNotUse;
import com.softserve.edu.rest.services.GuestServiceDoNotUse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.LifetimeRepository;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.UserServiceDoNotUse;
import com.softserve.edu.rest.tools.AllureUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("AllureTest")
@Feature("Login_Application_Test FEATURE")
public class LoginLogoutTest extends RestTestRunner {
	public static final Logger logger = LoggerFactory.getLogger(LoginLogoutTest.class); // org.slf4j.LoggerFactory
	//public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	//@Test
	public void checkVariables() {
		//LOGGER.info("checkVariables() START");
		System.out.println("JENKINS_LV426_PASSWORD = "
				+ System.getenv().get("JENKINS_LV426_PASSWORD"));
		System.out.println("password.variable = " + System.getProperty("password.variable"));
		//LOGGER.info("checkVariables() DONE");
	}

	@DataProvider
    public Object[][] correctUser() {
		//LOGGER.info("@DataProvider correctUser() DONE");
        return new Object[][]{
                { UserRepository.getAdmin() },
        };
    }
	
	//@Test(dataProvider = "correctUser")
	public void verifyLogin(User user) {
		//LOGGER.info("loginPositiveTest START, user = " + user);
        //log.debug("loginPositiveTest started!");
		//
        //Steps
        UserServiceDoNotUse userService = new GuestServiceDoNotUse()
        		.successfulUserLogin(user);
        logger.error("fjfj{}", user);
        //
        //Check
        //Assert.assertTrue(userService.isUserLogged(user));
        //
        //Step
        GuestServiceDoNotUse guestService = userService.logout();
        //Assert.assertFalse(guestService.isUserLogged(user));
        //
        AdminServiceDoNotUse adminService = guestService
        		.successfulAdminLogin(user);
        //
        guestService = adminService.logout();
        //
        //log.debug("loginPositiveTest finished!");
        //LOGGER.info("loginPositiveTest DONE, user = " + user);
    }

	@DataProvider
    public Object[][] correctAdminTime() {
		//LOGGER.info("@DataProvider correctUser() DONE");
        return new Object[][]{
                { UserRepository.getAdmin(), LifetimeRepository.getExtend() },
        };
    }

	@Description("Test Description: class LoginLogoutTest; verifyLifeTimeToken()")
	@Severity(SeverityLevel.CRITICAL)
	@Story("testApp2 STORY")
	@Issue("LVATQAOMS-776")
	@Link(name = "allure", type = "mylink")
	@Link("https://softserve.academy/")
	@Test(dataProvider = "correctAdminTime")
	public void verifyLifeTimeToken(User admin, Lifetime lifetime) {
		AllureUtils.isSaveJson = true;
		//LOGGER.info("loginPositiveTest START, user = " + user);
        //log.debug("loginPositiveTest started!");
		//
        // Steps
		GuestServiceDoNotUse guestService = loadApplication()
				.resetServiceToInitialState();
        //
        // Check
        Assert.assertTrue(guestService.getCurrentLifetime()
        		.equals(LifetimeRepository.getDefault()));
        //
        // Step
        AdminServiceDoNotUse adminService = guestService
        		.successfulAdminLogin(admin);
//        		.changeCurrentLifetime(lifetime);
        //
        // Check
        Assert.assertTrue(adminService.getCurrentLifetime()
        		.equals(LifetimeRepository.getExtend()));
        //
        // Return to Previous State
        guestService = adminService.logout();
        //
        //log.debug("loginPositiveTest finished!");
        //LOGGER.info("loginPositiveTest DONE, user = " + user);
    }

	//@Test(dataProvider = "correctAdminTime",//)
	//      expectedExceptions = RuntimeException.class)
	public void verifyException(User admin, Lifetime lifetime) {
		logger.info("verifyException START, user = " + admin.toString() + " lifetime = " + lifetime.toString());
		// log.debug("loginPositiveTest started!");
		//
		// Steps
		GuestServiceDoNotUse guestService = loadApplication()
				.resetServiceToInitialState();
		//
		// Check
		//guestService.updateLifetime();
		guestService.updateCurrentLifetime();
		//
		// log.debug("loginPositiveTest finished!");
		logger.info("verifyException DONE, user = " + admin.toString());
	}

}

package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.LogginedUser;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdministrationService extends UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(AdministrationService.class);

    private LogginedUser logginedUser;
//  private UsersService usersService;


    public AdministrationService(LogginedUser logginedUser) {
        super(logginedUser);
        this.logginedUser = logginedUser;
    }

    @Step("Go to user service")
    public UsersService gotoUsersService() {
        LOGGER.debug("Go to User Service");
        return new UsersService(logginedUser);
    }

    @Step("Go to User Service")
    public ItemService goToItemService() {
        LOGGER.debug("Go to Item Service");
        return new ItemService(logginedUser);
    }

    @Step("Go to Items Service")
    public ItemsService goToItemsService() {
        LOGGER.debug("Go to Items Service");
        return new ItemsService(logginedUser);
    }

    @Step("Go to Login Service")
    public LoginService goToLoginService() {
        return new LoginService();
    }

    @Step("Go to Cool Down Service")
    public CooldownService gotoCooldownService() {
        LOGGER.debug("Go to Cool down Service");
        return new CooldownService(logginedUser);
    }

    @Step("Go to Lock Service")
    public LockService gotoLockService() {
        LOGGER.debug("Go to Lock Service");
        return new LockService(logginedUser);
    }

    @Step("Go to Loggined User Service")
    public LogginedUsersService gotoLogginedUsersService() {
        LOGGER.debug("Go to Loggined User Service");
        return new LogginedUsersService(logginedUser);
    }

    @Step("Go to Loggined User")
    public LogginedUser getLogginedUser() {
        LOGGER.debug("Go to Loggined User");
        return logginedUser;
    }

    @Step("Go to Manage User Service")
    public ManageUserService gotoManageUserService() {
        LOGGER.debug("Go to Manage User Service");
        return new ManageUserService(logginedUser);
    }
}
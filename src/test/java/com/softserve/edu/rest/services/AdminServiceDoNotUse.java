package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.tools.RegexUtils;

public class AdminServiceDoNotUse extends UserServiceDoNotUse {
	private UsersService usersService;
	private TokensService tokensService;

	public AdminServiceDoNotUse(LoginedUser loginedUser) {
		super(loginedUser);
		checkLoginedAdmins();
	}
//	/*
	private void checkLoginedAdmins() {
		RestParameters urlParameters = new RestParameters()
				.addParameter(EParameters.TOKEN, loginedUser.getToken());
		SimpleEntity loginedAdmins = loginResource.httpGetLoginedAdmins(null, urlParameters);
		System.out.println("loginedAdmins: " + loginedAdmins);
		if (!RegexUtils.isTextContains("admin", loginedAdmins.getContent())) {
			// TODO Develop Custom Exception
            throw new RuntimeException("Error Admin Login. Response: " + loginedAdmins.getContent());
		}
	}
	/*
	@Step("Change_Current_Lifetime")
	public AdminServiceDoNotUse changeCurrentLifetime(Lifetime lifetime) {
		RestParameters bodyParameters = new RestParameters()
				.addParameter("token", loginedUser.getToken())
				.addParameter("time", lifetime.getTimeAsText());
		SimpleEntity simpleEntity = tokenlifetimeResource.httpPutAsEntity(null, null, bodyParameters);
		checkEntity(simpleEntity, "false", "Error Change Current Lifetime");
		return this;
	}*/
	
}

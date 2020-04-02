package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;

import io.qameta.allure.Step;

public class UserServiceDoNotUse extends GuestServiceDoNotUse {

	protected LoginedUser loginedUser;

	public UserServiceDoNotUse(LoginedUser loginedUser) {
		// super();
		this.loginedUser = loginedUser;
	}

	@Step("Logout")
	public GuestServiceDoNotUse logout() {
		RestParameters bodyParameters = new RestParameters()
				.addParameter("name", loginedUser.getUser().getName())
				.addParameter("token", loginedUser.getToken());
		SimpleEntity simpleEntity = loginResource
				.httpDeleteAsEntity(null, null, bodyParameters);
		checkEntity(simpleEntity, "false", "Error Logout");
		return new GuestServiceDoNotUse();
	}
	
}

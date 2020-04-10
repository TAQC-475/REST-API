package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;

import io.qameta.allure.Step;

public class UserServiceDoNotUse extends GuestServiceDoNotUse {

	protected LogginedUser logginedUser;

	public UserServiceDoNotUse(LogginedUser logginedUser) {
		// super();
		this.logginedUser = logginedUser;
	}

	@Step("Logout")
	public GuestServiceDoNotUse logout() {
		RestParameters bodyParameters = new RestParameters()
				.addParameter(EParameters.NAME, logginedUser.getUser().getName())
				.addParameter(EParameters.TOKEN, logginedUser.getToken());
		SimpleEntity simpleEntity = loginResource
				.httpDeleteAsEntity(null, null, bodyParameters);
		checkEntity(simpleEntity, "false", "Error Logout");
		return new GuestServiceDoNotUse();
	}
	
}

package com.softserve.edu.rest.dto;

import com.softserve.edu.rest.data.User;

public class LogginedUser {

	private User user;
	private String token;
	private long loginTime;
	
	public LogginedUser(User user, String token) {
		this.user = user;
		this.token = token;
		loginTime = System.currentTimeMillis();
	}

	public User getUser() {
		return user;
	}

	public String getToken() {
		return token;
	}

	public long getLoginTime() {
		return loginTime;
	}

	@Override
	public String toString() {
		return "LogginedUser{" +
				"user=" + user +
				", token='" + token + '\'' +
				", loginTime=" + loginTime +
				'}';
	}
}
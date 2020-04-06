package com.softserve.edu.rest.entity;

public class SimpleEntity {

	private String content;
	private String code;

	public SimpleEntity(String content, String code) {
		this.content = content;
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "SimpleEntity{" +
				"content='" + content + '\'' +
				", code='" + code + '\'' +
				'}';
	}
}

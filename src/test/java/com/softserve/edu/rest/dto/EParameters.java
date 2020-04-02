package com.softserve.edu.rest.dto;

public enum EParameters {

    NAME("name"),
    PASSWORD("password"),
    TOKEN("token"),
    ITEM("item"),
    INDEX("index"),
    RIGHTS("rights"),
    TIME("time"),
    OLD_PASSWORD("oldpassword"),
    NEW_PASSWORD("newpassword");

    private String parameter;

    EParameters(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return String.valueOf(parameter);
    }
}

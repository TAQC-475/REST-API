package com.softserve.edu.rest.data;

public enum EParameters {

    USER("user"),
    PASSWORD("password"),
    TOKEN("token"),
    ITEM("item"),
    RIGHTS("rights"),
    TIME("time"),
    OLD_PASSWORD("oldpassword"),
    NEW_PASSWORD("newpassword");

    private String parameter;

    EParameters(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    @Override
    public String toString() {
        return "EParameters{" +
                "parameter='" + parameter + '\'' +
                "} " + super.toString();
    }
}

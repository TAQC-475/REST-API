package com.softserve.edu.rest.tools;

public class CustomException extends Exception {

    public CustomException(){}

    public CustomException(String message){
        super(message);
    }
}

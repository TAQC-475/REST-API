package com.softserve.edu.rest.tools;

import com.softserve.edu.rest.entity.SimpleEntity;
import org.apache.commons.lang3.RandomStringUtils;

public class EntityUtils {
    public static final String SUCCESS_MARKER = "true";
    public static final String UNSUCCESS_MARKER = "false";
    private static EntityUtils instance;

    private EntityUtils() {
    }

    public static EntityUtils get() {
        if (instance == null) {
            instance = new EntityUtils();
        }
        return instance;
    }

    public SimpleEntity checkEntity(SimpleEntity result) {
        if (result.getContent() == null
                || result.getContent() == ""
                || result.getContent() == "false"
                || result.getContent() == "null") {
            throw new RuntimeException("Content is not found or Token time out");
        }
        return result;
    }

    public static boolean isUserActionSuccessful(SimpleEntity logoutEntity) {
        return logoutEntity.getContent().equalsIgnoreCase(SUCCESS_MARKER);
    }

    public static boolean isUserActionUnSuccessful(SimpleEntity logoutEntity) {
        return logoutEntity.getContent().equalsIgnoreCase(SUCCESS_MARKER);
    }

    public static String randomAlphaNumeric(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }
}
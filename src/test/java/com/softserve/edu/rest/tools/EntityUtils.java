package com.softserve.edu.rest.tools;

import com.softserve.edu.rest.entity.SimpleEntity;

public class EntityUtils {
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

    //Check if lock request is success
    public void checkLockEntity(SimpleEntity simpleEntity, String message) {
        if ((simpleEntity.getContent() == null) || (simpleEntity.getContent().isEmpty())
                || (simpleEntity.getContent().toLowerCase().equals("false"))) {
            throw new RuntimeException(message);
        }
    }
}
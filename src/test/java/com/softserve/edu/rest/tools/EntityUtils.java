package com.softserve.edu.rest.tools;

import com.softserve.edu.rest.entity.SimpleEntity;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;

public class EntityUtils {
    public static final String SUCCESS_MARKER = "true";
    public static final String UNSUCCESSFUL_MARKER = "false";
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
//        if (
//                result.getContent() == null
//                || result.getContent() == ""
//                || result.getContent() == "null") {
//            throw new RuntimeException("Content is not found or Token time out");
//        }
        return result;
    }

    public SimpleEntity deleteEntity(SimpleEntity result) {
        return result;
    }

    public SimpleEntity checkCooldownEntity(SimpleEntity result) {
        if (result.getContent() == null
                || result.getContent() == ""
                || result.getContent() == "null") {
            throw new RuntimeException("Content is not found or Token time out");
        }
        return result;
    }

    public static boolean isUserActionSuccessful(SimpleEntity entity) {
        return entity.getContent().equalsIgnoreCase(SUCCESS_MARKER);
    }

    public static boolean isUserActionUnSuccessful(SimpleEntity entity) {
        return entity.getContent().equalsIgnoreCase(UNSUCCESSFUL_MARKER);
    }

    /**
     * @param count symbols quantity
     * @return String of random letters and digits
     */
    public static String randomAlphaNumeric(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    //Check if lock request is success
    public void checkLockEntity(SimpleEntity simpleEntity, String message) {
        if ((simpleEntity.getContent() == null) || (simpleEntity.getContent().isEmpty())
                || (simpleEntity.getContent().toLowerCase().equals("false"))) {
            throw new RuntimeException(message);
        }
    }
}
package com.softserve.edu.rest.dto;

public final class RestUrlRepository {
    private static String server = "http://localhost:8080";

    private RestUrlRepository() {
    }

//	public static RestUrl getAdminAuthorized() {
//		return new RestUrl()
//				.addBaseUrl(server)
//				.addGetUrl("/login/users")
//				.addPostUrl("/login")
//				.addPutUrl("")
//				.addDeleteUrl("/logout");
//		// .addDeleteUrl("/logout");
//	}

    public static RestUrl getLogginedUsers() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/login/users")
                .addPostUrl("")
                .addPutUrl("")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getLogginedAdmins() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/login/admins")
                .addPostUrl("")
                .addPutUrl("")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getAuthentication() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("")
                .addPostUrl("/login")
                .addPutUrl("")
                .addDeleteUrl("/logout")
                .addPatchUrl("");
    }

    public static RestUrl getUser() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/user")
                .addPostUrl("/user")
                .addPutUrl("/user")
                .addDeleteUrl("/user")
                .addPatchUrl("");
    }


    public static RestUrl getApplication() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/reset")
                .addPostUrl("")
                .addPutUrl("")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getAliveTokens() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/login/tockens") //Jesus doesn't want me for a sunbeam
                .addPostUrl("")
                .addPutUrl("")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getTokenLifetime() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/tokenlifetime")
                .addPostUrl("")
                .addPutUrl("/tokenlifetime")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getUserItem() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/item/{index}/user/{name}")
                .addPostUrl("")
                .addPutUrl("")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getUserItems() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/item/user/{name}")
                .addPostUrl("")
                .addPutUrl("")
                .addDeleteUrl("");
    }

    public static RestUrl getItem() {
        return new RestUrl().addBaseUrl(server)
                .addGetUrl("/item/{index}")
                .addPostUrl("/item/{index}")
                .addPutUrl("/item/{index}")
                .addDeleteUrl("/item/{index}")
                .addPatchUrl("");
    }

    public static RestUrl getItems() {
        return new RestUrl().addBaseUrl(server)
                .addGetUrl("/items")
                .addPostUrl("")
                .addPutUrl("")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getUsers() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/users")
                .addPostUrl("")
                .addPutUrl("")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getCoolDownTime() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/cooldowntime")
                .addPostUrl("")
                .addPutUrl("/cooldowntime")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getItemsIndexes() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/itemindexes")
                .addPostUrl("")
                .addPutUrl("")
                .addDeleteUrl("")
                .addPatchUrl("");
    }

    public static RestUrl getLockedUser() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("")
                .addPostUrl("/locked/user/{name}")
                .addPutUrl("/locked/user/{name}")
                .addDeleteUrl("");
    }

    public static RestUrl getLockedUsers() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/locked/users")
                .addPostUrl("")
                .addPutUrl("/locked/reset")
                .addDeleteUrl("");
    }

    public static RestUrl getLockedAdmins() {
        return new RestUrl()
                .addBaseUrl(server)
                .addGetUrl("/locked/admins")
                .addPostUrl("")
                .addPutUrl("/locked/reset")
                .addDeleteUrl("");
    }
}


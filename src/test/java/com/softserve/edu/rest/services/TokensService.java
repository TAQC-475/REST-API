package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.resources.AliveTokensResource;

public class TokensService {
    private AliveTokensResource aliveTokensResource;
    private LoginedUser loginedAdmin;

    public TokensService(LoginedUser loginedAdmin){
        this.loginedAdmin = loginedAdmin;
        this.aliveTokensResource = new AliveTokensResource();
    }


}

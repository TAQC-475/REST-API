package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.AliveTokensResource;
import io.qameta.allure.Step;

public class TokensService extends AdministrationService{
    private LoginedUser loginedAdmin;
    private AliveTokensResource aliveTokensResource;


    public TokensService(LoginedUser loginedAdmin){
        super(loginedAdmin);
        this.loginedAdmin = loginedAdmin;
        this.aliveTokensResource = new AliveTokensResource();
    }
    protected void checkEntity(SimpleEntity simpleEntity,
                               String wrongMessage, String errorMessage) {
        if ((simpleEntity.getContent() == null)
                || (simpleEntity.getContent().isEmpty())
                || (simpleEntity.getContent().toLowerCase()
                .equals(wrongMessage.toLowerCase()))) {
            // TODO Develop Custom Exception
            throw new RuntimeException(errorMessage);
        }
    }
    public Lifetime getCurrentLifetime() {
        SimpleEntity simpleEntity = aliveTokensResource.httpGetAsEntity(null, null);
        return new Lifetime(simpleEntity.getContent());
    }
    public void updateLifetime() {
        aliveTokensResource.httpPostAsEntity(null, null, null);
    }

    public TokensService updateCurrentLifetime() {
        // checkEntity(simpleEntity, "false", "Error Change Current Lifetime");
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, "111111111111111")
                .addParameter(EParameters.TIME, new Lifetime("111111").getTimeAsText());
        SimpleEntity simpleEntity = aliveTokensResource.httpPutAsEntity(null, null, bodyParameters);
        return this;
    }

    public TokensService changeCurrentLifetime(Lifetime lifetime) {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedAdmin.getToken())
                .addParameter(EParameters.TIME, lifetime.getTimeAsText());
        SimpleEntity simpleEntity = aliveTokensResource.httpPutAsEntity(null, null, bodyParameters);
        //checkEntity(simpleEntity, "false", "Error Change Current Lifetime");
        return this;
    }

    public TokensService waitTokenLifeTime(Lifetime lifetime) {
        try {
            Thread.sleep(lifetime.getTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }
//    public boolean isUserLogged(User user) {
//
//        if (getAllLoggedUsers().contains(user.getName())) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}

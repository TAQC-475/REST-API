package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.CooldownTimeResource;
import com.softserve.edu.rest.tools.EntityUtils;

public class CooldownService{

    protected CooldownTimeResource cooldownResource;
    private LoginedUser loginedUser;
    private SimpleEntity response;

    public CooldownService() {
        cooldownResource = new CooldownTimeResource();
    }

    public CooldownService(LoginedUser loginedUser) {
        cooldownResource = new CooldownTimeResource();
        this.loginedUser = loginedUser;
    }

    public SimpleEntity getResponse() { return response; }

    public void setResponse(SimpleEntity response) { this.response = response; }

    public Lifetime getCooldownTime() {
        SimpleEntity simpleEntity = cooldownResource
                .httpGetAsEntity(null, null);
        EntityUtils.get().checkEntity(simpleEntity);
        return new Lifetime(simpleEntity.getContent());
    }

    public CooldownService changeCooldown(Lifetime lifetime) {
        System.out.println("user - "+loginedUser.getUser());
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken())
                .addParameter(EParameters.TIME, lifetime.getTimeAsText());
        SimpleEntity simpleEntity = cooldownResource
                .httpPutAsEntity(null, null, bodyParameters);
        setResponse(simpleEntity);
        EntityUtils.get().checkCooldownEntity(simpleEntity);
        return this;
    }

}

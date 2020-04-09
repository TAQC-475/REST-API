package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.CooldownTimeResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CooldownService{

    public static final Logger logger = LoggerFactory.getLogger(CooldownService.class);

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

    @Step("Getting cooldown time")
    public Lifetime getCooldownTime() {
        SimpleEntity simpleEntity = cooldownResource
                .httpGetAsEntity(null, null);
        EntityUtils.get().checkEntity(simpleEntity);
        return new Lifetime(simpleEntity.getContent());
    }

    @Step("Changing cooldown time")
    public CooldownService changeCooldown(Lifetime lifetime) {
        logger.debug("Change cooldown time START, new lifetime for setting = " + lifetime.getTime());
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken())
                .addParameter(EParameters.TIME, lifetime.getTimeAsText());
        SimpleEntity simpleEntity = cooldownResource
                .httpPutAsEntity(null, null, bodyParameters);
        setResponse(simpleEntity);
        logger.debug("Change cooldown time is DONE, response = "+simpleEntity.getContent());
        EntityUtils.get().checkCooldownEntity(simpleEntity);
        return this;
    }

}

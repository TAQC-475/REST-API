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
    private LoginedUser loginedUser;  //  ?

    public CooldownService() {
        cooldownResource = new CooldownTimeResource();
    }

    public CooldownService(LoginedUser loginedUser) {
        cooldownResource = new CooldownTimeResource();
        this.loginedUser = loginedUser;
    }

    public Lifetime getCooldownTime() {
        SimpleEntity result = cooldownResource
                .httpGetAsEntity(null, null);
        EntityUtils.get().checkEntity(result);
        return new Lifetime(result.getContent());
    }

    public Lifetime changeCooldown(Lifetime lifetime) {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken())  //  ?
                .addParameter(EParameters.TIME, lifetime.getTimeAsText());
        SimpleEntity result = cooldownResource
                .httpPutAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(result);
        if (result.getContent().equals("true")) return new Lifetime(lifetime.getTime());
        return new Lifetime(result.getContent());
    }


}

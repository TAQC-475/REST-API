package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.CooldownTimeResource;

public class CooldownService extends AdminServiceDoNotUse {

    protected CooldownTimeResource cooldownResource;

    public CooldownService(LoginedUser loginedUser) {
        super(loginedUser);
        cooldownResource = new CooldownTimeResource();
    }

    public AdminServiceDoNotUse changeCooldown(Lifetime lifetime) {
        RestParameters bodyParameters = new RestParameters()
                .addParameter("token", loginedUser.getToken())
                .addParameter("time", lifetime.getTimeAsText());
        SimpleEntity simpleEntity = cooldownResource
                .httpPutAsEntity(null, null, bodyParameters);
        checkEntity(simpleEntity, "false","The cooldown time was not been changed");
        return this;
    }

}

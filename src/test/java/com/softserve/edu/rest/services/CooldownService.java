package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.CooldownTimeResource;
import com.softserve.edu.rest.tools.EntityUtils;

public class CooldownService{

    protected CooldownTimeResource cooldownResource;

    public CooldownService() {
        cooldownResource = new CooldownTimeResource();
    }

    public Lifetime getCooldownTime() {
        SimpleEntity simpleEntity = cooldownResource
                .httpGetAsEntity(null, null);
        EntityUtils.get().checkEntity(simpleEntity);
        return new Lifetime(simpleEntity.getContent());
    }

//    public AdminServiceDoNotUse changeCooldown(Lifetime lifetime) {
//        RestParameters bodyParameters = new RestParameters()
//                .addParameter("token", loginedUser.getToken())
//                .addParameter("time", lifetime.getTimeAsText());
//        SimpleEntity simpleEntity = cooldownResource
//                .httpPutAsEntity(null, null, bodyParameters);
//        checkEntity(simpleEntity, "false","The cooldown time was not been changed");
//        return this;
//    }


}

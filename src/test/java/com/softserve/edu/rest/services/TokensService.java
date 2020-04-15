package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.Lifetime;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.AliveTokensResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokensService extends AdministrationService {
    public static final Logger logger = LoggerFactory.getLogger(TokensService.class);

    private LogginedUser loginedAdmin;
    private AliveTokensResource aliveTokensResource;


    public TokensService(LogginedUser loginedAdmin) {
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

    public TokensService changeCurrentLifetime(String lifetime) {
        logger.debug("changeCurrentLifetime START");
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedAdmin.getToken())
                .addParameter(EParameters.TIME, lifetime);
        SimpleEntity simpleEntity = aliveTokensResource.httpPutAsEntity(null, bodyParameters, null);
        checkEntity(simpleEntity, "false", "Error Change Current Lifetime");
        logger.debug("changeCurrentLifetime DONE");
        return this;
    }

    public TokensService changeCurrentLifetime(Lifetime lifetime) {
        return this.changeCurrentLifetime(lifetime.getTimeAsText());
    }

    public TokensService waitTokenLifeTime(Lifetime lifetime) {
        try {
            Thread.sleep(lifetime.getTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }
}

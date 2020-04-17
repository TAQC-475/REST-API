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
            throw new RuntimeException(errorMessage);
        }
    }

    /**
     * Preparing and sending GET request as logged in admin to get current token lifetime
     *
     * @return token lifetime
     */
    public Lifetime getCurrentLifetime() {
        SimpleEntity simpleEntity = aliveTokensResource.httpGetAsEntity(null, null);
        logger.debug("Token lifetime = {}", simpleEntity.getContent());
        return new Lifetime(simpleEntity.getContent());
    }

    /**
     * Change token lifetime
     *
     * @param lifetime to change
     * @return
     */
    public TokensService changeCurrentLifetime(String lifetime) {
        logger.debug("Changing token lifetime from {} to {}", getCurrentLifetime(), lifetime);
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedAdmin.getToken())
                .addParameter(EParameters.TIME, lifetime);
        SimpleEntity simpleEntity = aliveTokensResource.httpPutAsEntity(null, bodyParameters, null);
        checkEntity(simpleEntity, "false", "Error Change Current Lifetime");
        logger.debug("Token lifetime was change from {} to {}", getCurrentLifetime(), lifetime);
        return this;
    }

    public TokensService changeCurrentLifetime(Lifetime lifetime) {
        logger.debug("Changing token lifetime from {} to {}", getCurrentLifetime(), lifetime);
        return this.changeCurrentLifetime(lifetime.getTimeAsText());
    }

    /**
     * Waiting chosen time
     *
     * @param lifetime is time to wait
     * @return
     */
    public TokensService waitTokenLifeTime(Lifetime lifetime) {
        logger.debug("Time to wait:{}", lifetime);
        try {
            Thread.sleep(lifetime.getTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }
}

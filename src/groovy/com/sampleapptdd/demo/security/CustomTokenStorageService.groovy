package com.sampleapptdd.demo.security

import com.sampleapptdd.demo.AuthToken
import com.sampleapptdd.demo.type.Status
import com.sampleapptdd.demo.type.AuthTokenType
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenNotFoundException
import groovy.time.TimeCategory
import groovy.util.logging.Log4j

/**
 * This is only used by the spring security rest plugin.
 * Use AuthTokenService instead.
 * */
@Log4j
class CustomTokenStorageService implements TokenStorageService {

    int MINUTES_BEFORE_UPDATE

    /**
     * Public method here
     * */
    @Override
    Object loadUserByToken(String tokenValue) throws TokenNotFoundException {
        def existingToken = findExistingToken(tokenValue)
        if (existingToken && tokenBelongsToActiveUser(existingToken)) {
            updateLastUsed(existingToken)

            return existingToken.user
        }

        log.debug("Token ${tokenValue} not found")
        throw new TokenNotFoundException("Token ${tokenValue} not found")
    }

    /**
     * Please use AuthTokenService instead
     * */
    @Override
    @Deprecated
    void storeToken(String tokenValue, Object principal) {
    }

    /**
     * Please use AuthTokenService instead
     * */
    @Override
    @Deprecated
    void removeToken(String tokenValue) throws TokenNotFoundException {
    }

    /**
     * Private method here
     * */
    private void updateLastUsed(existingToken) {
        if (existingToken.authTokenType == AuthTokenType.LOGIN || existingToken.authTokenType == AuthTokenType.REMEMBER_LOGIN) {
            use(TimeCategory) {
                MINUTES_BEFORE_UPDATE = MINUTES_BEFORE_UPDATE != null ? MINUTES_BEFORE_UPDATE : 5 //if not injected use 5
                if (existingToken.lastUsed.time <= MINUTES_BEFORE_UPDATE.minutes.ago.time) {
                    existingToken.lastUsed = new Date()
                    existingToken.save(flush: true, failOnError: true)
                }
            }
        }
    }

    private findExistingToken(String tokenValue) {
        AuthToken authToken
        authToken = AuthToken.findByToken(tokenValue, [lock: true])

        return authToken
    }

    private boolean tokenBelongsToActiveUser(AuthToken existingToken) {
        existingToken.user.status == Status.ACTIVE
    }
}

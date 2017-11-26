package com.sampleapptdd.demo

import com.sampleapptdd.demo.type.AuthTokenType
import com.sampleapptdd.demo.exception.InvalidTokenException
import com.sampleapptdd.demo.exception.ResourceNotFoundException
import com.sampleapptdd.demo.common.AbstractValidateAndSaveService
import com.odobo.grails.plugin.springsecurity.rest.token.generation.TokenGenerator
import grails.transaction.Transactional

@Transactional
class AuthTokenService extends AbstractValidateAndSaveService {

    private final static int REMEMBER_LOGIN_EXPIRY_IN_MINUTES = 86400 //60 days
    private final static int LOGIN_EXPIRY_IN_MINUTES = 30

    final static int TOKEN_LIMIT = 5

    TokenGenerator tokenGenerator

    /**
     * Public method here
     * */
	//TODO: Create unit test for this method call
    AuthToken generateLoginToken(User user, AuthTokenType tokenType) {
        String token = tokenGenerator.generateToken()
        AuthToken authToken = new AuthToken(
                token: token,
                authTokenType: tokenType,
                lastUsed: new Date(),
                dateCreated: new Date(),
                user: user
        )
        validateAndSave(authToken)

        return authToken
    }

    //TODO: Create unit test for this method call
    AuthToken fetchByUser(User user) {
        AuthToken authToken = AuthToken.findByUser(user)
        if (!authToken) {
            throw new ResourceNotFoundException()
        }

        return authToken
    }

    //TODO: Create unit test for this method call
    AuthToken fetchByToken(String token) {
        AuthToken authToken = AuthToken.findByToken(token)
        if (!authToken) {
            throw new ResourceNotFoundException()
        }

        return authToken
    }

	//TODO: Create unit test for this method call
    void removeToken(String tokenValue) throws InvalidTokenException {
        def existingToken = findExistingToken(tokenValue)
        if (!existingToken) {
            throw new InvalidTokenException("Token ${tokenValue} not found")
        }

        if (existingToken.authTokenType in [AuthTokenType.LOGIN, AuthTokenType.REMEMBER_LOGIN]) {
            existingToken.delete()
        } else {
            throw new InvalidTokenException("Cannot delete token of AuthTokenType: API")
        }
    }

    /**
     * Private method here
     * */
    private AuthToken findExistingToken(String tokenValue) {
        return AuthToken.findByToken(tokenValue)
    }
}

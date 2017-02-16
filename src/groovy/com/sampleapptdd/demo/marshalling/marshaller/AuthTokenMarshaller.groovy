package com.sampleapptdd.demo.marshalling.marshaller

import com.sampleapptdd.demo.AuthToken
import grails.converters.JSON

class AuthTokenMarshaller {
    void register() {
        JSON.registerObjectMarshaller(AuthToken) { AuthToken authToken ->
            return [
                    authToken: authToken.token,
                    type: authToken.authTokenType.toString(),
                    dateCreated: authToken.dateCreated,
                    lastUsed: authToken.lastUsed,
                    user: authToken.user,
                    _entityType: 'AuthToken'
            ]
        }
    }
}

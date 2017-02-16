package com.sampleapptdd.demo

import com.sampleapptdd.demo.type.AuthTokenType
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class AuthToken {

    String token
    AuthTokenType authTokenType
    Date lastUsed
    Date dateCreated
    User user

    static constraints = {
        token nullable: false, unique: true
        authTokenType nullable: false
        user nullable: false
        dateCreated nullable: true
        lastUsed nullable: true
    }

    static mapping = {
        id generator:'sequence', column:'id', params:[sequence:'auth_token_sequence']
        version false
    }
}

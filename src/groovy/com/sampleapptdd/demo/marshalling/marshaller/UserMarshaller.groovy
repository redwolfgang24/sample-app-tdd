package com.sampleapptdd.demo.marshalling.marshaller

import com.sampleapptdd.demo.User
import grails.converters.JSON

class UserMarshaller {
    void register() {
        JSON.registerObjectMarshaller(User) { User user ->
            return [
                    id: user.id.toString(),
                    firstName: user.firstName,
                    middleName: user.middleName,
                    lastName: user.lastName,
                    suffix: user.suffix,
                    fullName: user.fullNameOrEmailAddress,
                    username: user.username,
                    emailAddress: user.emailAddress,
                    verified: user.verified,
                    lastLogin: user.lastLogin,
                    roles: user.roles*.authority.name,
                    _entityType: 'User'
            ]
        }
    }
}

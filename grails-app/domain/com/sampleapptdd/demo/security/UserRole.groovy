package com.sampleapptdd.demo.security

import com.sampleapptdd.demo.User

class UserRole implements Serializable {

    Role role
    User user

    static mapping = {
        id composite: ['role', 'user']
        version false
    }
}

package com.sampleapptdd.demo.security

import com.sampleapptdd.demo.type.RoleAuthority

class Role {

    RoleAuthority authority

    static constraints = {
        authority unique: true
    }

    static mapping = {
        id generator:'sequence', column:'id', params:[sequence:'role_sequence']
    }
}

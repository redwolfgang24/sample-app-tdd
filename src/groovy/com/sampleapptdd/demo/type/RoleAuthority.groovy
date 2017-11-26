package com.sampleapptdd.demo.type

import org.springframework.security.core.GrantedAuthority

enum RoleAuthority implements GrantedAuthority {

    ROLE_ADMIN('ROLE_ADMIN'),
    ROLE_USER('ROLE_USER')

    String name

    RoleAuthority(String name) {
        this.name = name
    }

    String toString() {
        return name
    }

    static RoleAuthority findByName(String name) {
        return values().find { name.toUpperCase() == it.name }
    }

    @Override
    String getAuthority() {
        return name
    }
}

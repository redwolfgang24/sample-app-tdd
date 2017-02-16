package com.sampleapptdd.demo.type

import org.springframework.security.core.GrantedAuthority

public enum RoleAuthority implements GrantedAuthority {

    ROLE_ADMIN('ROLE_ADMIN'),
    ROLE_USER('ROLE_USER')

    String name

    public RoleAuthority(String name) {
        this.name = name
    }

    String toString() {
        return name
    }

    public static RoleAuthority findByName(String name) {
        return values().find { name.toUpperCase() == it.name }
    }

    @Override
    String getAuthority() {
        return name
    }
}

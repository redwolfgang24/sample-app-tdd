package com.sampleapptdd.demo.security

import com.sampleapptdd.demo.type.RoleAuthority
import grails.transaction.Transactional

@Transactional
class RoleService {

    public Role fetchRoleByAuthority(RoleAuthority authority) {
        return fetchByAuthority(authority)
    }

    public Role fetchAdminRole() {
        return fetchByAuthority(RoleAuthority.ROLE_ADMIN)
    }

    public Role fetchUserRole() {
        return fetchByAuthority(RoleAuthority.ROLE_USER)
    }

    private Role fetchByAuthority(RoleAuthority authority) {
        return Role.findByAuthority(authority)
    }
}

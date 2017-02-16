package com.sampleapptdd.demo.security

import com.sampleapptdd.demo.User
import com.sampleapptdd.demo.common.AbstractValidateAndSaveService
import com.sampleapptdd.demo.type.RoleAuthority
import grails.transaction.Transactional

@Transactional
class UserRoleService extends AbstractValidateAndSaveService {

    RoleService roleService

    public void save(User user, Set<RoleAuthority> userRoleName) {
        for(RoleAuthority roleAuthority : userRoleName) {
            UserRole userRole = new UserRole(
                    user: user,
                    role: roleService.fetchRoleByAuthority(roleAuthority)
            )
            validateAndSave(userRole)
        }
    }

    public UserRole save(User user, RoleAuthority roleAuthority) {
        UserRole userRole = new UserRole(
                user: user,
                role: roleService.fetchRoleByAuthority(roleAuthority)
        )
        validateAndSave(userRole)

        return userRole
    }

    public void update(User user, Set<RoleAuthority> roles) {
        if (roles) {
            List<UserRole> userRoles = UserRole.findAllByUser(user)
            removeObsoleteRoles(userRoles, roles)
            assignNewRoles(user, fetchUserRoles(user), roles)
        }
    }

    public Set<Role> fetchAllRoles(User user) {
        return user.roles
    }

    /**
     * Private method below
     * */
    private void removeObsoleteRoles(List<UserRole> rolesOfUser, Set<RoleAuthority> roleAuthoritiesToAssign) {
        rolesOfUser.each { existingUserRole ->
            RoleAuthority existingRoleAuthority = existingUserRole.role.authority
            if (!roleAuthoritiesToAssign.contains(existingRoleAuthority)) {
                existingUserRole.delete()
            }
        }
    }

    private void assignNewRoles(User user, Set<Role> existingRoles, Set<RoleAuthority> rolesToBeAssigned) {
        rolesToBeAssigned.each { roleName ->
            Role role = roleService.fetchRoleByAuthority(roleName)
            if(!existingRoles.contains(role)) {
                UserRole userRole = new UserRole(
                        user: user,
                        role: role
                )
                validateAndSave(userRole)
            }
        }
    }

    private Set<Role> fetchUserRoles(User user) {
        return UserRole.findAllByUser(user)*.role
    }

    private boolean isRoleAdmin(User user) {
        return isRoleExist(user, RoleAuthority.ROLE_ADMIN)
    }

    private boolean isRoleUser(User user) {
        return isRoleExist(user, RoleAuthority.ROLE_USER)
    }

    private boolean isRoleExist(User user, RoleAuthority authority) {
        if(UserRole.findByUserAndRole(user, roleService.fetchRoleByAuthority(authority))) {
            return true
        } else {
            return false
        }
    }
}

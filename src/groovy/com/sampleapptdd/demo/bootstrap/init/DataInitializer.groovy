package com.sampleapptdd.demo.bootstrap.init

import com.sampleapptdd.demo.User
import com.sampleapptdd.demo.security.Role
import com.sampleapptdd.demo.type.RoleAuthority
import com.sampleapptdd.demo.type.Status

class DataInitializer implements BootstrapInitializerComponent {

    @Override
    void initialize() {
        executeDataInitializer()
    }

    private void executeDataInitializer() {
        Role adminRole = new Role(authority: RoleAuthority.ROLE_ADMIN).save(flush: true, failOnError: true)
        Role userRole = new Role(authority: RoleAuthority.ROLE_USER).save(flush: true, failOnError: true)

        User adminUser = new User(
                firstName: "Learning",
                middleName: "Web",
                lastName: "Framework",
                username: "sampleapp",
                password: "password",
                emailAddress: "sampleapp@yopmail.com",
                roles: [adminRole],
                status: Status.ACTIVE,
                verified: true,
                dateVerified: new Date()
        ).save(flush: true, failOnError: true)

        User user = new User(
                firstName: "Learning Two",
                middleName: "Web",
                lastName: "Framework",
                username: "sampleapp2",
                password: "password",
                emailAddress: "sampleapp2@yopmail.com",
                roles: [userRole],
                status: Status.ACTIVE,
                verified: true,
                dateVerified: new Date()
        ).save(flush: true, failOnError: true)
    }
}

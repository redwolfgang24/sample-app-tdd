package com.sampleapptdd.demo

import com.sampleapptdd.demo.dto.UserDto
import com.sampleapptdd.demo.security.Role
import com.sampleapptdd.demo.type.RoleAuthority
import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@Build([User, Role])
@TestFor(UserService)
class UserServiceSpec extends Specification {

    def setup() {
        User.metaClass.encodePassword = {}
    }

    def cleanup() {
    }

    void "update method should update user."() {
        given:
        Role role1 = Role.build(authority: RoleAuthority.ROLE_USER)
        Role role2 = Role.build(authority: RoleAuthority.ROLE_ADMIN)
        User currentUser = User.build(
                firstName: "Learning",
                middleName: "Web",
                lastName: "Framework",
                suffix: "Sr.",
                username: "sampleapp",
                password: "password",
                emailAddress: "sampleapp@yopmail.com",
                verified: "true",
                dateVerified: new Date(),
                roles: [
                        role1, role2
                ]
        )

        UserDto userDto = new UserDto(
                firstName: "New Learning",
                middleName: "New Web",
                lastName: "New Framework",
                suffix: "Sr.",
                username: "newsampleapp",
                emailAddress: "newsampleapp@yopmail.com",
                roles: [
                        role1, role2
                ]
        )

        when:
        User updatedUser = service.update(currentUser, userDto)

        then:
        updatedUser.firstName == userDto.firstName
        updatedUser.middleName == userDto.middleName
        updatedUser.lastName == userDto.lastName
        updatedUser.suffix == userDto.suffix
        updatedUser.username == userDto.username
        updatedUser.emailAddress == userDto.emailAddress
        updatedUser.roles == userDto.roles
    }
}

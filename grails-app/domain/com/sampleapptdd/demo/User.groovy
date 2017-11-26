package com.sampleapptdd.demo

import com.sampleapptdd.demo.type.Status
import com.sampleapptdd.demo.type.RoleAuthority
import com.sampleapptdd.demo.security.Role
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class User {

    transient springSecurityService
    UUID id = generateUserId()
    String firstName
    String middleName
    String lastName
    String suffix
    String username
    String password
    String emailAddress
    Status status = Status.ACTIVE
    boolean enabled = true
    boolean verified = false
    Date dateVerified
    Date dateCreated = new Date()
    Date lastUpdated = new Date()
    Date lastLogin = new Date()

    static transients = ['springSecurityService']

    static hasMany = [
            roles: Role
    ]

    static constraints = {
        firstName nullable: false
        middleName nullable: true
        lastName nullable: false
        suffix nullable: true
        username blank: false, unique: true
        password blank: false
        emailAddress email: true, nullable: false, unique: true
        status nullable: false
        dateVerified nullable: true
        dateCreated nullable: true
        lastUpdated nullable: true
        lastLogin nullable: true
    }

    static mapping = {
        table 'users'
        password column: '`password`'
        id generator: 'uuid2', type: 'pg-uuid'
    }

    def beforeInsert() {
        if (password != null) {
            encodePassword()
        }
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }

    String getFullNameOrEmailAddress() {
        if (this.firstName && this.lastName) {
            return "${this.firstName} ${this.middleName ? this.middleName : ''} ${this.lastName} ${this.suffix ? this.suffix : ''}"
        } else {
            return this.emailAddress
        }
    }

    boolean isRolesContains(RoleAuthority authority) {
        boolean isContains = false
        roles.each { role ->
            if (role.authority == authority || role.authority.equals(authority)) {
                isContains = true
            }
        }

        return isContains
    }

    UUID generateUserId() {
        return UUID.randomUUID()
    }
}

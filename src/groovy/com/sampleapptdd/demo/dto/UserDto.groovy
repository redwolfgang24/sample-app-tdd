package com.sampleapptdd.demo.dto

import com.sampleapptdd.demo.security.Role

class UserDto {
    String firstName
    String middleName
    String lastName
    String suffix
    String username
    String password
    String emailAddress
    Set<Role> roles
}

package com.sampleapptdd.demo.security

import com.sampleapptdd.demo.User
import com.sampleapptdd.demo.UserService
import com.sampleapptdd.demo.type.ApiError
import com.sampleapptdd.demo.exception.VerificationException
import com.sampleapptdd.demo.exception.AuthenticationException
import com.sampleapptdd.demo.common.AbstractValidateAndSaveService
import org.springframework.security.authentication.encoding.PasswordEncoder
import grails.transaction.Transactional

@Transactional
class AuthenticationService extends AbstractValidateAndSaveService {

    UserService userService
    PasswordEncoder passwordEncoder

    User authenticate(String username, String password) {
        User authenticatedUser = authenticateUser(username, password)
        updateLastLogin(authenticatedUser)

        return authenticatedUser
    }

    /**
     * Private method below
     * */
    private User authenticateUser(String username, String password) {

        User authenticatedUser = userService.fetchByEmailOrUserName(username)
        if (!authenticatedUser) {
            throw new AuthenticationException("Unregistered authenticatedUser!")
        }

        if (!authenticatedUser.verified) {
            throw new VerificationException(ApiError.NOT_VERIFIED_USER)
        }

        if (!password || !passwordEncoder.isPasswordValid(authenticatedUser.password, password, null)) {
            throw new AuthenticationException("Mismatched password!")
        }

        return authenticatedUser
    }

    private void updateLastLogin(User user) {
        user.lastLogin = new Date()
        validateAndSave(user)
    }
}

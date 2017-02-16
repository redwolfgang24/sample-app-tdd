package com.sampleapptdd.demo.api.v1

import com.sampleapptdd.demo.AuthTokenService
import com.sampleapptdd.demo.common.ApplicationController
import com.sampleapptdd.demo.exception.InvalidTokenException
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.http.HttpStatus

class LogoutController extends ApplicationController {

    static responseFormats = ['json']

    AuthTokenService authTokenService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def logout() {
        def tokenValue = request.getHeader("Authorization")
        try {
            tokenValue = tokenValue.substring(7)
            authTokenService.removeToken(tokenValue)
            sendResponse([message: "Successfully logout."])
        } catch (InvalidTokenException tokenException) {
            sendResponse(HttpStatus.BAD_REQUEST, [message: "Invalid AuthToken"])
        }
    }
}

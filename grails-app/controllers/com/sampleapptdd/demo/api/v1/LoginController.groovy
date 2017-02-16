package com.sampleapptdd.demo.api.v1

import com.sampleapptdd.demo.User
import com.sampleapptdd.demo.AuthToken
import com.sampleapptdd.demo.AuthTokenService
import com.sampleapptdd.demo.common.ApplicationController
import com.sampleapptdd.demo.security.AuthenticationService
import com.sampleapptdd.demo.exception.ResourceNotFoundException
import com.sampleapptdd.demo.type.RequestFieldType
import com.sampleapptdd.demo.type.AuthTokenType
import com.sampleapptdd.demo.type.ApiError
import org.springframework.http.HttpStatus

class LoginController extends ApplicationController {

    AuthTokenService authTokenService
    AuthenticationService authenticationService

    def login() {
        requiredJsonFieldsPresent(["username", "password", "remember"])
        validateEmptyFields(["username", "password", "remember"])

        Map jsonRequest = jsonFieldsAreValid([
                remember : RequestFieldType.BOOLEAN
        ])

        try {
            User user = authenticationService.authenticate(jsonRequest.username, jsonRequest.password)
            AuthToken authToken = authTokenService.generateLoginToken(user, AuthTokenType.LOGIN)
            sendResponse([user: user, accessToken: authToken.token])
        } catch (ResourceNotFoundException e) {
            sendResponse(HttpStatus.NOT_FOUND, ApiError.UNREGISTERED_USER)
        }
    }
}

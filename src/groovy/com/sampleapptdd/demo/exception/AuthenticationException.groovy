package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.type.ApiError

class AuthenticationException extends RuntimeException {

    ApiError apiError = ApiError.AUTHENTICATION_FAILED

    public AuthenticationException(String message){
        super(message)
    }

    public AuthenticationException(ApiError apiError) {
        this.apiError = apiError
    }
}

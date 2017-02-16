package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.type.ApiError

class VerificationException extends RuntimeException {

    ApiError apiError = ApiError.NOT_VERIFIED_USER

    public VerificationException(String message){
        super(message)
    }

    public VerificationException(ApiError apiError) {
        this.apiError = apiError
    }
}

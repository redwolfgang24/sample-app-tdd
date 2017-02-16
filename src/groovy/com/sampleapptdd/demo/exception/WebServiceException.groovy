package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.type.ApiError

class WebServiceException extends RuntimeException {

    ApiError apiError

    WebServiceException(ApiError apiError = ApiError.SERVICE_UNAVAILABLE, Throwable cause = null) {
        super(apiError.errorMessage, cause)
        this.apiError = apiError
    }

    WebServiceException(Throwable cause) {
        super(ApiError.SERVICE_UNAVAILABLE.errorMessage, cause)
        this.apiError = ApiError.SERVICE_UNAVAILABLE
    }
}

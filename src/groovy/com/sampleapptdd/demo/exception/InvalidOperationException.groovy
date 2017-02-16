package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.type.ApiError

class InvalidOperationException extends SampleAppException {

    ApiError apiError

    public InvalidOperationException(ApiError apiError = ApiError.INVALID_OPERATION) {
        super(apiError.errorMessage)
        this.apiError = apiError
    }
}

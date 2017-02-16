package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.type.ApiError

class InvalidFileException extends SampleAppException {

    ApiError apiError

    InvalidFileException(ApiError apiError = ApiError.INVALID_FILE, Throwable cause = null) {
        super(apiError.errorMessage, cause)
        this.apiError = apiError
    }
}

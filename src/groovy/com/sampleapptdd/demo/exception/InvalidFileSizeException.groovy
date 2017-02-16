package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.type.ApiError

class InvalidFileSizeException extends InvalidFileException {

    InvalidFileSizeException(ApiError apiError = ApiError.INVALID_FILE_SIZE, Throwable cause = null) {
        super(apiError, cause)
        this.apiError = apiError
    }
}

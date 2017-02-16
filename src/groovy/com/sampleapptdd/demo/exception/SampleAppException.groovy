package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.type.ApiError

abstract class SampleAppException extends RuntimeException {

    SampleAppException(String message, Throwable cause = null) {
        super(message, cause)
    }

    abstract ApiError getApiError()
}

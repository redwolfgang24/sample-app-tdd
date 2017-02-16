package com.sampleapptdd.demo.common

import com.sampleapptdd.demo.dto.ApiErrorMessage
import com.sampleapptdd.demo.type.ApiError
import org.springframework.http.HttpStatus

trait ResponseSender {

    void sendResponse() {
        render status: HttpStatus.NO_CONTENT
    }

    void sendResponse(def responseData) {
        respond (responseData)
    }

    void sendResponse(HttpStatus status, def responseData) {
        response.status = status.value()
        respond (responseData)
    }

    void sendResponse(HttpStatus status, ApiError apiError) {
        response.status = status.value()
        respond new ApiErrorMessage(apiError).toMap()
    }

    void sendResponse(HttpStatus status, Map responseData, ApiError apiError) {
        response.status = status.value()
        respond responseData + new ApiErrorMessage(apiError).toMap()
    }
}

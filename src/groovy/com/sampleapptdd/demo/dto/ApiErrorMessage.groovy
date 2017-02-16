package com.sampleapptdd.demo.dto

import com.sampleapptdd.demo.type.ApiError
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class ApiErrorMessage {

    /**
     * Constructor for api error messages due to validation errors
     * */
    public ApiErrorMessage(ApiError apiError, List<ValidationErrorMessage> validationErrorMessages) {
        this.apiError = apiError
        this.validationErrorMessages = validationErrorMessages
    }

    /**
     * Constructor for api error messages not due to validation errors
     * */
    public ApiErrorMessage(ApiError apiError) {
        this.apiError = apiError
        this.validationErrorMessages = []
    }

    ApiError apiError

    List<ValidationErrorMessage> validationErrorMessages

    /**
     *	Used for marshalling because Spock(Unit test) doesn't recognize the custom marshallers.
     */
    public Map toMap() {
        def map = [
                errorCode: apiError.errorCode,
                errorMessage: apiError.errorMessage
        ]

        if (apiError == ApiError.VALIDATION_ERROR) {
            def validationErrorList = []
            validationErrorMessages.each { validationErrorMessage ->
                validationErrorList.add(validationErrorMessage.toMap())
            }
            map.put('validationErrors', validationErrorList)
        }

        return map
    }
}

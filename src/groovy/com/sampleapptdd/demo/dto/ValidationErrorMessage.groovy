package com.sampleapptdd.demo.dto

import com.sampleapptdd.demo.type.ValidationError

class ValidationErrorMessage {

    //TODO: Add handling for params
    public ValidationErrorMessage(ValidationError validationError, String field) {
        this.validationError = validationError
        this.field = field
    }

    public ValidationErrorMessage(ValidationError validationError, String field, Map<String, String> params) {
        this.validationError = validationError
        this.field = field
        this.params = params
    }

    public ValidationErrorMessage(ValidationError validationError) {
        this.validationError = validationError
        this.field = null
    }

    ValidationError validationError

    String field

    Map<String, String> params

    /**
     *	Used for marshalling because Spock(Unit test) doesn't recognize the custom marshallers.
     */
    public Map toMap() {
        return [
                field: field?: ' ',
                errorCode: validationError.errorCode,
                params: params?: ' ',
                errorMessage: validationError.errorMessage
        ]
    }
}

package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.dto.ApiErrorMessage
import com.sampleapptdd.demo.dto.ValidationErrorMessage
import com.sampleapptdd.demo.type.ApiError
import com.sampleapptdd.demo.type.ValidationError

class ValidationWithObjectException extends RuntimeException {

    private final Map<String, String> CONSTRAINTS_MAP = [minSize: 'minSize', size: 'range']

    Object invalidObject
    List<ValidationErrorMessage> errorMessages

    ValidationWithObjectException(String msg, Object invalidObject, List<ValidationErrorMessage> errorMessages = []) {
        super(msg)
        this.invalidObject = invalidObject
        this.errorMessages = errorMessages
    }

    ApiErrorMessage getApiErrorMessage() {
        def validationErrorMessages = []
        invalidObject.errors.fieldErrors.each {
            String[] errorCodeArray = it.code.split('\\.')
            String constraintName = errorCodeArray[0]
            if (CONSTRAINTS_MAP.containsKey(constraintName)) {
                String constraintPropertyName = CONSTRAINTS_MAP[constraintName]
                def constraintValue = invalidObject.constraints?."${it.field}"?.getAppliedConstraint(constraintName)?."$constraintPropertyName"
                if(constraintValue instanceof Integer) {
                    validationErrorMessages.add(new ValidationErrorMessage(ValidationError.valueOfCode(it.code), it.field, ["numberValue": constraintValue]))
                } else if (constraintValue instanceof Range) {
                    validationErrorMessages.add(new ValidationErrorMessage(ValidationError.valueOfCode(it.code), it.field, ["rangeValue": constraintValue]))
                }
            } else {
                validationErrorMessages.add(new ValidationErrorMessage(ValidationError.valueOfCode(it.code), it.field))
            }
        }
        errorMessages.each { message ->
            validationErrorMessages.add(message)
        }
        invalidObject.errors.globalErrors.each {
            validationErrorMessages.add(new ValidationErrorMessage(ValidationError.valueOfCode(it.code)))
        }
        return new ApiErrorMessage(ApiError.VALIDATION_ERROR, validationErrorMessages)
    }
}

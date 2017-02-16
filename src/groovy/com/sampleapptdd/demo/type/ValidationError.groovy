package com.sampleapptdd.demo.type

enum ValidationError {

    FIELD_TOO_SHORT("minSize.notmet", "Field is required to be at least X characters."),
    FIELD_MUST_BE_WITHIN_RANGE("value.must.be.within.range", "Field's value must be within the min and max range."),
    FIELD_VALUE_NOT_SUPPORTED("value.not.supported", "Field's value is currently not supported"),
    FIELD_VALUE_MUST_BE_TRUE_OR_FALSE("invalid.boolean.value", "Field's value must be true or false"),
    FIELD_VALUE_MUST_BE_NUMERIC("value.not.numeric", "Field's value must be numeric"),
    FIELD_TOO_SMALL("size.toosmall", "Field must not be less than X"),
    FIELD_TOO_BIG("size.toobig", "Field must not be greater than X"),
    FIELD_NOT_NULLABLE("nullable", "Field must not be null"),
    FIELD_INVALID_ROLE("role.invalid", "Specified role is invalid"),
    FIELD_SHOULD_BE_UNIQUE("unique", "That Field is taken, please try another"),
    FIELD_NOT_ALPHANUM("alphanum", "Field must only contain letters and numbers."),
    FIELD_INVALID_PASSWORD("password.invalid", "Field must be alphanumeric or punctuation or symbols and must consists of at least 8 characters and not more than 255 characters."),
    FIELD_EMPTY("empty", "Field should not be empty"),
    FIELD_EXIST("exist", "Field already exist"),
    FIELD_INVALID_EMAIL("email.invalid", "Field email address format is invalid"),
    FIELD_BLANK("blank", "Field should not be blank"),
    FIELD_INVALID_FORMAT("matches.invalid", "Field does not match required format"),
    FIELD_PARSE_ERROR("text.cannot.be.parsed", "Field contains invalid syntax for template"),
    FIELD_INVALID_URL("field.invalid.url", "Invalid url."),
    FIELD_INVALID_COUNTRY("field.invalid.country.name", "Country specified is invalid")

    /**
     *
     * Error code of a field.
     * It can be retrieved from FieldError's code property
     *
     * */
    String grailsErrorCode
    String errorMessage
    String errorCode

    ValidationError(String grailsErrorCode, String errorMessage) {
        this.grailsErrorCode = grailsErrorCode
        this.errorCode = name()
        this.errorMessage = errorMessage
    }

    static ValidationError valueOfCode(String grailsErrorCode) {
        values().find { it.grailsErrorCode == grailsErrorCode}
    }

    String toString() {
        return errorCode
    }
}

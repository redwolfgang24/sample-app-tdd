package com.sampleapptdd.demo.type

enum ApiError {

    AUTHENTICATION_FAILED("Wrong Username/Email and/or password combination."),
    AUTHENTICATION_FAILED_WORKER_NOT_ALLOWED("Worker not allowed to login."),
    AUTHENTICATION_FAILED_CLIENT_NOT_ALLOWED("Client not allowed to login."),
    AUTHENTICATION_FAILED_ADMIN_FACILITY_NOT_ALLOWED("Admin facility not allowed to login."),
    AUTHENTICATION_FAILED_ADMIN_NOT_ALLOWED("Admin not allowed to login."),
    FAILED_TO_CONFIRM_PASSWORD("Mismatch password and confirm password."),
    INCOMPLETE_REQUIRED_FIELDS("Please fill-up all required fields."),
    PASSWORD_ALREADY_USED("Invalid password, please try another unique password."),
    INVALID_EMAIL_ADDRESS("Invalid email address, please input a valid email address."),
    EMAIL_ADDRESS_ALREADY_USED("Invalid email address, please try another unique email address."),
    USERNAME_ALREADY_USED("Invalid username, please try another unique username."),
    USERNAME_PASSWORD_VALIDATION_ERROR("Username and password should not be the same."),
    FATAL_WEB_SERVICE("A fatal error has occurred, please contact our support team."),
    ACCESS_DENIED("Does not have the authority to access this"),
    VALIDATION_ERROR("Input data validation error"),
    INCOMPLETE_REQUEST("You have incomplete request"),
    INVALID_REQUEST("Your request is invalid"),
    PREFERENCE_ERROR("Error retrieving Preference data"),
    UN_UPDATABLE_RESOURCE("The targeted resource cannot be updated"),
    INTERNAL_SERVER_ERROR("Service temporarily not available."),
    RESOURCE_NOT_FOUND("The targeted resource could not be found"),
    URL_NOT_FOUND("The requested url does not exist"),
    SERVICE_UNAVAILABLE("Failure occured in server"),
    PASSWORD_MISMATCH("Wrong password supplied"),
    UNREGISTERED_USER("User is not yet registered"),
    CONFIRMATION_CODES_MISMATCH("Wrong confirmation code supplied"),
    EMAIL_CONFIRMATION_TOKEN_MISMATCH("Wrong email confirmation token supplied"),
    RECOVERY_TOKEN_MISMATCH("Wrong account recovery token supplied"),
    RECOVERY_TOKEN_BLOCKED("Account recovery token is currently blocked"),
    NO_RECOVERY_REQUEST("User did not request for an account recovery"),
    SENDING_EMAIL_FAILED("Unable to send email"),
    INVALID_OPERATION("Cannot process this request."),
    SERVICE_ERROR("Service failed to execute"),
    EMAIL_VERIFICATION_REQUIRED("Please verify your email and set your password."),
    NO_CHANGE("There is nothing to update"),
    MALFORMED_REQUEST("The json request cannot be parsed"),
    BAD_REQUEST("The request is invalid. It probably lacks an Authorization Bearer Header"),
    METHOD_NOT_ALLOWED("The http method used is not supported"),
    INVALID_LANGUAGE_CODE("Unsupported language code"),
    USER_IS_NOT_AN_ADMIN("User must be an admin for this operation."),
    NOTHING_TO_UPDATE("Nothing to update."),
    NOT_VERIFIED_USER("You must confirm verification email."),
    NO_CHANNEL_ACCESS("Current user is not permitted to access the specified channel."),
    INVALID_CONTENT_TYPE("Content type is invalid."),
    INVALID_FILE_SIZE("Please follow the required size and accepted files.")

    String errorMessage
    String errorCode

    ApiError(String errorMessage) {
        this.errorCode = name()
        this.errorMessage = errorMessage
    }

    String toString() {
        return errorCode
    }
}

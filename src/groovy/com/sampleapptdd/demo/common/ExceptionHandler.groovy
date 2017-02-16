package com.sampleapptdd.demo.common

import com.sampleapptdd.demo.dto.ApiErrorMessage
import com.sampleapptdd.demo.exception.AuthenticationException
import com.sampleapptdd.demo.exception.IncompleteRequestException
import com.sampleapptdd.demo.exception.InternalServerException
import com.sampleapptdd.demo.exception.InvalidArgumentsException
import com.sampleapptdd.demo.exception.InvalidRequestException
import com.sampleapptdd.demo.exception.NothingToUpdateException
import com.sampleapptdd.demo.exception.ResourceNotFoundException
import com.sampleapptdd.demo.exception.SampleAppException
import com.sampleapptdd.demo.exception.UnauthorizedOperationException
import com.sampleapptdd.demo.exception.ValidationWithObjectException
import com.sampleapptdd.demo.exception.VerificationException
import com.sampleapptdd.demo.type.ApiError
import org.springframework.http.HttpStatus
import groovy.util.logging.Log4j

@Log4j
trait ExceptionHandler {

    def handleJaylexApiExceptionException(SampleAppException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond new ApiErrorMessage(e.apiError).toMap()
    }

    def handleValidationWithObjectException(ValidationWithObjectException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond e.getApiErrorMessage().toMap()
    }

    def handleVerificationException(VerificationException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond new ApiErrorMessage(ApiError.NOT_VERIFIED_USER).toMap()
    }

    def handleIncompleteRequestException(IncompleteRequestException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond new ApiErrorMessage(ApiError.INCOMPLETE_REQUEST).toMap()
    }

    def handleInvalidRequestException(InvalidRequestException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond new ApiErrorMessage(ApiError.INVALID_REQUEST).toMap()
    }

    def handleResourceNotFoundException(ResourceNotFoundException e) {
        response.status = HttpStatus.NOT_FOUND.value()
        respond new ApiErrorMessage(e.apiError).toMap()
    }

    def handleNothingToUpdateException(NothingToUpdateException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond new ApiErrorMessage(e.apiError).toMap()
    }

    def handleAuthenticationException(AuthenticationException e) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        respond new ApiErrorMessage(e.apiError).toMap()
    }

    def handleInvalidArgumentsException(InvalidArgumentsException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond message: "One or more of the arguments passed in the request were invalid. Each element that failed validation is included in the fault details along with the reason for failure."
    }

    def handleInternalServerException(InternalServerException e) {
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        respond message: "An unusual error occurred on the platform. If this error occurs please contact support for further instruction."
    }

    def handleUnauthorizedOperationException(UnauthorizedOperationException e) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        respond message: "Unauthorized."
    }
}

package com.sampleapptdd.demo.common

import com.sampleapptdd.demo.User
import com.sampleapptdd.demo.exception.ValidationWithObjectException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import grails.validation.ValidationException
import groovy.util.logging.Log4j

@Log4j
abstract class AbstractValidateAndSaveService {

    public def validateAndSave(def object, Map params = [flush: true, failOnError: true]) {
        try {
            object.save(params)
        } catch (ValidationException e) {
            log.debug "Invalid [$object]"
            object.errors.allErrors.each { logError(it) }

            throw new ValidationWithObjectException("Invalid [$object]", object)
        }
    }

    public def batchValidateAndSave(List objects, int flushBatchSize = 100) {

        int index = 0

        User.withSession { session ->

            objects.each { object ->
                if (0 == index % flushBatchSize) {
                    validateAndSave(object, [flush: true])
                    session.clear()
                } else {
                    validateAndSave(object, [flush: false])
                }
                index++
            }

            session.flush()
            session.clear()
        }

        return objects
    }

    private void logError(ObjectError error) {
        if (error instanceof FieldError) {
            def rejectedValue = error.rejectedValue
            log.debug "FIELD=${error.field}, VALUE=${rejectedValue}, TYPE=[${rejectedValue.getClass()}]"
        } else {
            log.debug "    * $error"
        }
    }
}

package com.sampleapptdd.demo.common

import com.sampleapptdd.demo.exception.IncompleteRequestException
import com.sampleapptdd.demo.exception.InvalidRequestException
import com.sampleapptdd.demo.exception.JSONToMapParsingException
import com.sampleapptdd.demo.type.RequestFieldType
import com.sampleapptdd.demo.util.RequestUtils
import org.codehaus.groovy.grails.web.json.JSONObject

trait RequestValidator {

    /**
     *
     * Validates then converts json requests to appropriate format.
     * Throws IncompleteRequestException if json request is incomplete.
     *
     * @param requiredFields list of names of the json fields that are required
     * @throws com.sampleapptdd.demo.exception.IncompleteRequestException
     *
     * */
    def void requiredJsonFieldsPresent(List<String> requiredFields) {
        String missingField = RequestUtils.requiredJsonFieldsPresent(request.JSON, requiredFields)
        if (missingField) {
            throw new IncompleteRequestException()
        }
    }

    def void requiredParamsPresent(List<String> requiredFields) {
        String missingField = RequestUtils.requiredJsonFieldsPresent(new JSONObject(params), requiredFields)
        if (missingField) {
            throw new IncompleteRequestException()
        }
    }

    def void validateEmptyFields(List<String> requiredFields) {
        boolean validationResult = RequestUtils.requiredJsonFieldsNotBlank(request.JSON, requiredFields)
        if(!validationResult) {
            throw new IncompleteRequestException()
        }
    }

    /**
     *
     * Validates then converts json requests to appropriate format.
     * Throws InvalidRequestException if validation fails.
     *
     * @param fieldsToValidate key should be the name of the json field and value should be the type of that field
     * @return Map that contains the converted values. Key would be the name of the field (same name with the json request) and value would be the converted value
     * @throws com.sampleapptdd.demo.exception.InvalidRequestException
     *
     * */
    def Map<String, Object> jsonFieldsAreValid(Map<String, RequestFieldType> fieldsToValidate) {
        return validate(fieldsToValidate, request.JSON)
    }

    /**
     *
     * Validates then converts params requests to appropriate format.
     * Throws InvalidRequestException if validation fails.
     *
     * @param fieldsToValidate key should be the name of the params field and value should be the type of that field
     * @return Map that contains the converted values. Key would be the name of the field (same name with the params) and value would be the converted value
     * @throws com.sampleapptdd.demo.exception.InvalidRequestException
     *
     * */
    def Map<String, Object> paramFieldsAreValid(Map<String, RequestFieldType> fieldsToValidate) {
        return validate(fieldsToValidate, params)
    }

    boolean jsonListWithNoKeyIsValid(String name, List listToValidate, RequestFieldType type) {
        def fieldtoValidate = [name : listToValidate]
        return validate( [name : type] , fieldtoValidate)
    }

    private def validate(Map<String, RequestFieldType> fieldsToValidate, def requestFields) {
        try{
            return RequestUtils.parseRequestToMap(requestFields, fieldsToValidate)
        } catch(JSONToMapParsingException e) {
            throw new InvalidRequestException(e.field, e.value)
        }
    }
}

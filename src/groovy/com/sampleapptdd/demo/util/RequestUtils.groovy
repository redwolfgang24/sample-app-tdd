package com.sampleapptdd.demo.util

import com.sampleapptdd.demo.exception.JSONToMapParsingException
import com.sampleapptdd.demo.type.RequestFieldType
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class RequestUtils {

    public static String requiredJsonFieldsPresent(JSONObject json, List<String> requiredFields) {
        String missingField = requiredFields.find {
            List<String> keyLevels = it.tokenize(".").reverse()
            !containsJsonKey(keyLevels, json)
        }

        return missingField
    }

    private static boolean containsJsonKey(List<String> keyLevels, JSONObject jsonObject) {
        List<String> remainingKeyLevelsToCheck = keyLevels.collect()
        String searchKey = remainingKeyLevelsToCheck.pop()

        if(remainingKeyLevelsToCheck.isEmpty()) {
            return jsonObject.has(searchKey)
        }

        if(!jsonObject.has(searchKey))  {
            return false
        }

        def jsonValue = jsonObject.get(searchKey)
        if (jsonValue instanceof JSONObject) {
            return containsJsonKey(remainingKeyLevelsToCheck, jsonValue)
        } else if (jsonValue instanceof JSONArray) {
            jsonValue.every { element -> containsJsonKey(remainingKeyLevelsToCheck, element) }
        }   else {
            return false
        }
    }

    public static boolean requiredJsonFieldsNotBlank(JSONObject json, List<String> requiredFields) {

        def passed = true
        requiredFields.each { requiredField ->

            def value = fetchValueToValidateFromRequest(requiredField, json)

            if(passed && (value instanceof JSONArray || value instanceof List)){
                passed = checkListForNullValues(value)
            }else if (passed && convertEmptyOrJSONNullValuesToNull(value) == null) {
                passed = false
            }
        }
        return passed
    }

    private static boolean checkListForNullValues(def list){

        boolean passed = (list.isEmpty())? false : true

        list.each{ arrayValue ->
            if(passed && arrayValue instanceof JSONObject){
                passed = checkListForNullValues(arrayValue.values())
            }else if(passed && arrayValue instanceof JSONArray){
                passed = checkListForNullValues(arrayValue)
            }else if(passed && convertEmptyOrJSONNullValuesToNull(arrayValue) == null) {
                passed = false
            }
        }

        return passed
    }

    private static def convertEmptyOrJSONNullValuesToNull(def value){
        return (value.toString().trim() == "null" || value.toString().trim() == '') ? null : value
    }

    private static def fetchValueToValidateFromRequest(String requiredField, Map request){
        def fieldValue

        if(requiredField.contains('.')){
            def dissectedRequiredFields = requiredField.tokenize(".")

            def tempValue = request
            dissectedRequiredFields.each{ field ->
                tempValue = tempValue[field]
            }

            fieldValue = tempValue
        }else{
            fieldValue = request[requiredField]
        }

        return fieldValue
    }

    public static Map parseRequestToMap(Map json, Map<String, RequestFieldType> fieldsToValidate) {
        return parseRequest(json, fieldsToValidate, '')
    }

    private static Map parseRequest(Map json, Map<String, RequestFieldType> fieldsToValidate, String accumulatedRequestField) {

        def parsedJson = [:]

        json.each { requestField, value ->
            if (value instanceof JSONObject) {
                String jsonObjAccumulatedKey = accumulatedRequestField + requestField + "."
                parsedJson[requestField] = parseRequest(value, fieldsToValidate, jsonObjAccumulatedKey)
            } else if (value instanceof JSONArray) {
                String jsonArrayAccumulatedKey = accumulatedRequestField + requestField
                parsedJson[requestField] = parseJSONArray(value, fieldsToValidate, jsonArrayAccumulatedKey)
            } else {
                parsedJson[requestField] = validateAndConvertValue(value, accumulatedRequestField + requestField, fieldsToValidate)
            }
        }
        return parsedJson
    }

    private static List parseJSONArray(JSONArray jsonArray, Map<String, RequestFieldType> fieldsToValidate, String accumulatedRequestField) {

        List parsedJSONArray = []

        jsonArray.eachWithIndex { value, index ->
            def parsedValue
            if (value instanceof JSONObject) {
                def jsonAccumulatedRequestField = accumulatedRequestField + "." + index + "."
                parsedValue = parseRequest(value, fieldsToValidate, jsonAccumulatedRequestField)
            } else if (value instanceof JSONArray) {
                parsedValue = parseJSONArray(value, fieldsToValidate, accumulatedRequestField)
            } else {
                parsedValue = validateAndConvertValue(value, accumulatedRequestField, fieldsToValidate)
            }
            parsedJSONArray.add(parsedValue)
        }
        return parsedJSONArray
    }

    private static def validateAndConvertValue(def checkedValue, def requestField, Map<String, RequestFieldType> fieldsToValidate) {

        checkedValue = convertJsonNullToNull(checkedValue)
        if (checkedValue && fieldsToValidate["$requestField"] != null) {
            checkedValue = fieldsToValidate["$requestField"].convert(checkedValue.toString())
            if (checkedValue == null) throw new JSONToMapParsingException(requestField, checkedValue)
        }
        return checkedValue
    }

    private static def convertJsonNullToNull(def value) {
        return value.toString().trim() == "null" ? null : value
    }
}

package com.sampleapptdd.demo.type

import java.text.ParseException
import org.apache.commons.lang3.EnumUtils
import org.apache.commons.validator.routines.EmailValidator

enum RequestFieldType {

    ROLE_AUTHORITY {
        @Override
        def convert(String fieldValue) {
            if(EnumUtils.isValidEnum(RoleAuthority.class, fieldValue.toUpperCase())) {
                return RoleAuthority.findByName(fieldValue)
            } else {
                return null
            }
        }
    }, BOOLEAN {
        @Override
        def convert(String fieldValue) {
            if (fieldValue == 'true' || fieldValue == 'false') {
                return fieldValue.toBoolean()
            }
            return null
        }
    }, NUMBER {
        @Override
        def convert(String fieldValue) {
            if (fieldValue.isNumber() && fieldValue.toInteger() > 0) {
                return fieldValue.toInteger()
            }
            return null
        }
    }, BIG_DECIMAL {
        @Override
        def convert(String fieldValue) {
            if (fieldValue.isBigDecimal()) {
                return fieldValue.toBigDecimal()
            }
            return null
        }
    }, BIG_INTEGER {
        @Override
        def convert(String fieldValue) {
            if (fieldValue.isBigInteger()) {
                return fieldValue.toBigInteger()
            }
            return null
        }
    }, INTEGER {
        @Override
        def convert(String fieldValue) {
            if (fieldValue.isInteger() && fieldValue.toInteger() >= 0) {
                return fieldValue.toInteger()
            }
            return null
        }
    }, ID {
        @Override
        def convert(String fieldValue) {
            if (fieldValue.isLong() && fieldValue.toLong() > 0) {
                return fieldValue.toLong()
            }
            return null
        }
    }, UUID {
        @Override
        def convert(String fieldValue) {
            try {
                return java.util.UUID.fromString(fieldValue)
            } catch(IllegalArgumentException e) {
                return null
            }
        }
    }, DOUBLE {
        @Override
        def convert(String fieldValue) {
            try {
                return Double.parseDouble(fieldValue)
            } catch(NumberFormatException e) {
                return null
            }
        }
    }, POSITIVE_NUMBER {
        @Override
        def convert(String fieldValue) {
            if (fieldValue.isNumber() && fieldValue.toBigDecimal() > 0.00) {
                return fieldValue.toBigDecimal()
            }
            return null
        }
    }, DATE {
        String standardDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"
        @Override
        def convert(String fieldValue) {
            try {
                return Date.parse(standardDateFormat, fieldValue)
            } catch (ParseException e) {
                return null
            }
        }

        def getStandardDateFormat() {
            return standardDateFormat
        }
    }, DATE_SANS_SECONDS {
        String standardDateFormat = "yyyy-MM-dd'T'HH:mm"
        @Override
        def convert(String fieldValue) {
            try {
                return Date.parse(standardDateFormat, fieldValue)
            } catch (ParseException e) {
                return null
            }
        }

        def getStandardDateFormat() {
            return standardDateFormat
        }
    }, EMAIL_ADDRESS {
        @Override
        def convert(String fieldValue) {
            if (EmailValidator.getInstance().isValid(fieldValue)) {
                return fieldValue
            }
            return null
        }
    }, STATUS {
        @Override
        def convert(String fieldValue) {
            String status = fieldValue
            if(status.toUpperCase() == "IN ACTIVE") {
                status = "IN_ACTIVE"
            }

            if(EnumUtils.isValidEnum(Status.class, status)) {
                return Status.findByName(status)
            } else {
                return null
            }
        }
    }, URL {
        @Override
        def convert(String fieldValue) {
            String urlString = fieldValue.contains("http://") || fieldValue.contains("https://") || fieldValue.contains("ftp://") ? fieldValue : "http://${fieldValue}"
            if(UrlValidator.getInstance().isValid(urlString) ) {
                return fieldValue
            } else {
                return null
            }
        }
    }
    abstract def convert(String fieldValue)
}

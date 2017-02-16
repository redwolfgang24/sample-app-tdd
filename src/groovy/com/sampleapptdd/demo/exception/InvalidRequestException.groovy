package com.sampleapptdd.demo.exception

class InvalidRequestException extends RuntimeException {

    String field
    def value

    InvalidRequestException() {}

    InvalidRequestException(String field, def value) {
        this.field = field
        this.value = value
    }
}

package com.sampleapptdd.demo.exception

class JSONToMapParsingException extends RuntimeException {

    String field
    def value

    JSONToMapParsingException (String field, def value) {
        this.field = field
        this.value = value
    }

    public Map generateErrorMap(){
        return [errorField:field]
    }
}

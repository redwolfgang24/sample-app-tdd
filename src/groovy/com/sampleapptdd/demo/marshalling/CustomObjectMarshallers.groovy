package com.sampleapptdd.demo.marshalling

class CustomObjectMarshallers {

    List marshallers = []

    def register() {
        marshallers.each{ it.register() }
    }
}

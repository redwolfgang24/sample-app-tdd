package com.sampleapptdd.demo.token.generator

class UUIDTokenGenerator implements TokenGenerator {

    @Override
    String generateToken() {
        return UUID.randomUUID().toString().replaceAll('-', '')
    }
}

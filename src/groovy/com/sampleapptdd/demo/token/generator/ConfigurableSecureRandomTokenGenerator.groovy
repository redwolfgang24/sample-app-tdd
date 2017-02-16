package com.sampleapptdd.demo.token.generator

import java.security.SecureRandom
import org.apache.commons.lang.RandomStringUtils

class ConfigurableSecureRandomTokenGenerator {

    String generateToken(int length) {
        generateToken(length, true)
    }

    String generateToken(int length, boolean includeUpperCase) {
        SecureRandom secureRandom = new SecureRandom()

        String token
        if (includeUpperCase) {
            token = RandomStringUtils.random(length, 0, 61, true, true, (('A'..'Z')+('a'..'z')+('0'..'9')).join().toCharArray(),secureRandom)
        } else {
            token = RandomStringUtils.random(length, 0, 35, true, true, (('a'..'z')+('0'..'9')).join().toCharArray(),secureRandom)
        }

        return token
    }
}

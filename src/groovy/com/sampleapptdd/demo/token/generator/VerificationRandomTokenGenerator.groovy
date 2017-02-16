package com.sampleapptdd.demo.token.generator

import org.apache.commons.lang.RandomStringUtils
import java.security.SecureRandom

class VerificationRandomTokenGenerator implements TokenGenerator {

    @Override
    String generateToken() {
        SecureRandom secureRandom = new SecureRandom()
        return RandomStringUtils.random(10, 0, 61, true, true, (('A'..'Z')+('a'..'z')+('0'..'9')).join().toCharArray(), secureRandom)
    }
}

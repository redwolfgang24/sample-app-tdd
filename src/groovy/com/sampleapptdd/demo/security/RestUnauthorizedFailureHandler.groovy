package com.sampleapptdd.demo.security

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.http.HttpStatus
import groovy.util.logging.Log4j

@Log4j
class RestUnauthorizedFailureHandler implements AuthenticationFailureHandler {

    @Override
    void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info "Setting status code to ${HttpStatus.UNAUTHORIZED.value()}"
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value())
    }
}

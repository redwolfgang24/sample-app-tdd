package com.sampleapptdd.demo.security

import com.sampleapptdd.demo.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.authority.SimpleGrantedAuthority
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService
import com.odobo.grails.plugin.springsecurity.rest.RestAuthenticationProvider
import com.odobo.grails.plugin.springsecurity.rest.RestAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.util.Assert
import groovy.util.logging.Log4j

@Log4j
class CustomRestAuthenticationProvider extends RestAuthenticationProvider {

    UserRoleService userRoleService
    TokenStorageService tokenStorageService

    static final GrantedAuthority NO_ROLE = new SimpleGrantedAuthority(SpringSecurityUtils.NO_ROLE)

    /**
     * Returns an authentication object based on the token value contained in the authentication parameter. To do so,
     * it uses a {@link com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService}.
     * @throws org.springframework.security.core.AuthenticationException
     */
    @Override
    Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Assert.isInstanceOf(RestAuthenticationToken, authentication, "Only RestAuthenticationToken is supported")
        RestAuthenticationToken authenticationRequest = authentication
        RestAuthenticationToken authenticationResult = new RestAuthenticationToken(authenticationRequest.tokenValue)

        if (authenticationRequest.tokenValue) {
            log.debug "Trying to validate token ${authenticationRequest.tokenValue}"
            User authenticatedUser
            def authorities
            def password
            User.withTransaction {
                authenticatedUser = tokenStorageService.loadUserByToken(authenticationRequest.tokenValue)
                password = authenticatedUser.password

                authorities = userRoleService.fetchAllRoles(authenticatedUser).collect { new SimpleGrantedAuthority(it.authority.name) }

                authorities = authorities ?: [NO_ROLE]

                authenticationResult = new RestAuthenticationToken(authenticatedUser, password, authorities, authenticationRequest.tokenValue)

                SecurityContext securityContext = new SecurityContextImpl()
                securityContext.setAuthentication(authenticationResult)
                SecurityContextHolder.setContext(securityContext)

                log.debug "Authentication result: ${authenticationResult}"
            }
        }

        return authenticationResult
    }

    boolean supports(Class<?> authentication) {
        return RestAuthenticationToken.isAssignableFrom(authentication)
    }
}

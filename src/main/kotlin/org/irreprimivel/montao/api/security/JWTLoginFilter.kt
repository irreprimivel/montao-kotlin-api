package org.irreprimivel.montao.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.irreprimivel.montao.api.model.AccountCredentials
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter(url: String, authManager: AuthenticationManager) : AbstractAuthenticationProcessingFilter(
        AntPathRequestMatcher(url)) {
    init {
        authenticationManager = authManager
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val inputStream = request?.inputStream
        val credentials = ObjectMapper().readValue(inputStream, AccountCredentials::class.java)
        return authenticationManager.authenticate(UsernamePasswordAuthenticationToken(credentials.username,
                                                                                      credentials.password,
                                                                                      emptyList()))
    }

    override fun successfulAuthentication(request: HttpServletRequest,
                                          response: HttpServletResponse,
                                          chain: FilterChain,
                                          authResult: Authentication) {
        TokenAuthenticationService.addAuthentication(response, authResult.name)
    }
}
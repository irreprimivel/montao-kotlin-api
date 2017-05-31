package org.irreprimivel.montao.api.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


object TokenAuthenticationService {
    const val EXPIRATION_TIME: Long = 864_000_000
    const val SECRET: String = "ThisIsASecret"
    const val TOKEN_PREFIX: String = "Bearer"
    const val HEADER_STRING: String = "Authorization"

    fun addAuthentication(res: HttpServletResponse, username: String) {
        val jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt)
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            val user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .body
                    .subject

            return if (user != null) UsernamePasswordAuthenticationToken(user, null, emptyList()) else null
        }
        return null
    }
}
package com.example.prioritask.security.jwt

import com.example.prioritask.security.UserDetailsImpl
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.security.Key
import java.util.Date

private const val COOKIE_DURATION_IN_SECONDS = 24L * 60L * 60L

@Component
class JwtUtils(
    @Value("\${security.jwt.secret}")
    private val jwtSecret: String,
    @Value("\${security.jwt.expirationMs}")
    private val jwtExpirationMs: Int,
    @Value("\${security.jwt.cookieName}")
    private val jwtCookie: String

) {
    private val logger = KotlinLogging.logger {}

    fun getJwtFromCookies(request: HttpServletRequest): String? {
        return WebUtils.getCookie(request, jwtCookie)?.value
    }

    fun generateJwtCookie(userPrincipal: UserDetailsImpl): ResponseCookie {
        val jwt = generateTokenFromUsername(userPrincipal.username)

        return ResponseCookie.from(jwtCookie, jwt)
            .path("/api")
            .maxAge(COOKIE_DURATION_IN_SECONDS)
            .httpOnly(true)
            .build()
    }

    fun getCleanJwtCookie(): ResponseCookie {
        return ResponseCookie.from(jwtCookie)
            .path("/api")
            .build()
    }

    fun getUsernameFromJwtToken(token: String): String {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).body.subject
    }

    fun isJwtTokenValid(authToken: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken)
            return true
        } catch (ex: MalformedJwtException) {
            logger.error { "Invalid Jwt token: ${ex.message}" }
        } catch (ex: ExpiredJwtException) {
            logger.error { "Jwt token is expired: ${ex.message}" }
        } catch (ex: UnsupportedJwtException) {
            logger.error { "Jwt token is unsupported: ${ex.message}" }
        } catch (ex: IllegalArgumentException) {
            logger.error { "Jwt claims string is empty: ${ex.message}" }
        }
        return false
    }

    private fun key(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
    }

    private fun generateTokenFromUsername(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact()
    }
}
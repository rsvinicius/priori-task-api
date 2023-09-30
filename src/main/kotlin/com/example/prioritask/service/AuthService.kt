package com.example.prioritask.service

import com.example.prioritask.model.constants.ResponseBodyConstants.EMAIL_ALREADY_TAKEN
import com.example.prioritask.model.constants.ResponseBodyConstants.USERNAME_ALREADY_TAKEN
import com.example.prioritask.model.constants.ResponseBodyConstants.USER_REGISTERED
import com.example.prioritask.model.constants.ResponseBodyConstants.USER_SIGNED_OUT
import com.example.prioritask.model.entity.User
import com.example.prioritask.model.request.AuthenticationRequest
import com.example.prioritask.model.request.RegistrationRequest
import com.example.prioritask.model.response.AuthenticationResponse
import com.example.prioritask.repository.UserRepository
import com.example.prioritask.security.UserDetailsImpl
import com.example.prioritask.security.jwt.JwtUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService private constructor(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder
) {
    fun authenticateUser(authRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val userDetails = authentication.principal as UserDetailsImpl

        val jwtCookie = jwtUtils.generateJwtCookie(userDetails)

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(AuthenticationResponse(userDetails.getId(), userDetails.username, userDetails.getEmail()))
    }

    fun registerUser(registrationRequest: RegistrationRequest): ResponseEntity<String> {
        if(userRepository.existsByUsername(registrationRequest.username)) {
            return ResponseEntity.badRequest().body(USERNAME_ALREADY_TAKEN)
        }

        if(userRepository.existsByEmail(registrationRequest.email)) {
            return ResponseEntity.badRequest().body(EMAIL_ALREADY_TAKEN)
        }

        val user = User(
            username = registrationRequest.username,
            password = encoder.encode(registrationRequest.password),
            email = registrationRequest.email
        )

        userRepository.save(user)

        return ResponseEntity.ok().body(USER_REGISTERED)
    }

    fun logoutUser(): ResponseEntity<String> {
        val cookie = jwtUtils.getCleanJwtCookie()

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(USER_SIGNED_OUT)
    }
}
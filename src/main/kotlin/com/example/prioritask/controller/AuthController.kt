package com.example.prioritask.controller

import com.example.prioritask.model.request.AuthenticationRequest
import com.example.prioritask.model.request.RegistrationRequest
import com.example.prioritask.model.response.AuthenticationResponse
import com.example.prioritask.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody authenticationRequest: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> {
        return authService.authenticateUser(authenticationRequest)
    }

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody registrationRequest: RegistrationRequest
    ): ResponseEntity<String> {
        return authService.registerUser(registrationRequest)
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<String> {
        return authService.logoutUser()
    }
}
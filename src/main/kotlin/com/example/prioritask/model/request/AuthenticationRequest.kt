package com.example.prioritask.model.request

import jakarta.validation.constraints.NotBlank

data class AuthenticationRequest(
    @NotBlank
    val username: String,
    @NotBlank
    val password: String
)

package com.example.prioritask.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegistrationRequest(
    @Email
    val email: String,
    @NotBlank
    val fullName: String,
    @NotBlank
    val username: String,
    @NotBlank
    val password: String
)

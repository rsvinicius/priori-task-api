package com.example.prioritask.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Account (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val username: String,
    val email: String,
    val password: String
)

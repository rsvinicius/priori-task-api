package com.example.prioritask.service

import com.example.prioritask.exception.UnauthorizedUserException
import com.example.prioritask.model.entity.User
import com.example.prioritask.repository.UserRepository
import com.example.prioritask.security.UserDetailsImpl
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun getUserById(userId: Long): User {
        return userRepository.findUserById(userId)
    }

    fun getUserBySecurityContext(): User {
        return userRepository.findUserById(getUserId())
    }

    fun getUserId(): Long {
        return getUserDetails().getId()
    }

    fun getUserName(): String {
        return getUserDetails().username
    }

    fun getUserFullName(): String {
        return getUserDetails().getFullName()
    }

    fun getUserEmail(): String {
        return getUserDetails().getEmail()
    }

    fun checkUserIdConsistency(taskUserId: Long, userId: Long? = null) {
        val id = userId ?: getUserId()

        if (taskUserId != id) {
            throw UnauthorizedUserException("This task belongs to another user")
        }
    }

    private fun getUserDetails() = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
}
package com.example.prioritask.service

import com.example.prioritask.repository.UserRepository
import com.example.prioritask.security.UserDetailsImpl
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findUserByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return UserDetailsImpl(user)
    }
}
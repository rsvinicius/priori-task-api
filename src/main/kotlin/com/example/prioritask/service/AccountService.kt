package com.example.prioritask.service

import com.example.prioritask.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountRepository: AccountRepository) {
    fun getAccountById(userId: Long) = accountRepository.findAccountById(userId)
}

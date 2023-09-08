package com.example.prioritask.repository

import com.example.prioritask.model.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findAccountById(id: Long): Account
}

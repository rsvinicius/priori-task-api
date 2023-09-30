package com.example.prioritask.repository

import com.example.prioritask.model.entity.Task
import com.example.prioritask.model.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {
    fun findAllByUser(pageable: Pageable, user: User) : Page<Task>
}
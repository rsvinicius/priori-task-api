package com.example.prioritask.model.entity

import com.example.prioritask.util.enum.TaskPriorityEnum
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var title: String,

    var description: String?,

    var dueDate: LocalDate?,

    @Enumerated(EnumType.STRING)
    var priority: TaskPriorityEnum,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var account: Account,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category?
)
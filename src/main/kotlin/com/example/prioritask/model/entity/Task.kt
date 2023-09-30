package com.example.prioritask.model.entity

import com.example.prioritask.model.enums.TaskPriorityEnum
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
    val id: Long = 0,
    var title: String,
    var description: String?,
    var dueDate: LocalDate?,
    @Enumerated(EnumType.STRING)
    var priority: TaskPriorityEnum,
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,
    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category?
){
    constructor() : this(0, "", null, null, TaskPriorityEnum.NONE, User(), null)
}

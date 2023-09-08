package com.example.prioritask.model.dto

import com.example.prioritask.util.enum.TaskPriorityEnum
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Null
import jakarta.validation.constraints.PositiveOrZero
import java.time.LocalDate

data class TaskDto(
    @Null(message = "Please ignore id on request body", groups = [OnCreate::class, OnUpdate::class])
    val id: Long?,
    @NotBlank(message = "Task title must be provided", groups = [OnCreate::class])
    val title: String?,
    val description: String?,
    val dueDate: LocalDate?,
    val priority: TaskPriorityEnum? = TaskPriorityEnum.NONE,
    @PositiveOrZero(message = "User Id must be provided")
    val userId: Long,
    val categoryId: Long?
)
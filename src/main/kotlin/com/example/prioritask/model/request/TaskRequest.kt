package com.example.prioritask.model.request

import com.example.prioritask.model.enums.TaskPriorityEnum
import com.example.prioritask.utils.validators.FutureOrCurrentDate
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class TaskRequest(
    @field:NotBlank(message = "Task title must be provided")
    val title: String,
    val description: String?,
    @FutureOrCurrentDate
    val dueDate: LocalDate?,
    val priority: TaskPriorityEnum = TaskPriorityEnum.NONE,
    val categoryId: Long?
)
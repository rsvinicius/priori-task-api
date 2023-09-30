package com.example.prioritask.model.request

import com.example.prioritask.model.enums.TaskPriorityEnum
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class TaskRequest(
    @NotBlank(message = "Task title must be provided")
    val title: String,
    val description: String?,
    val dueDate: LocalDate?,
    val priority: TaskPriorityEnum = TaskPriorityEnum.NONE,
    val categoryId: Long?
)
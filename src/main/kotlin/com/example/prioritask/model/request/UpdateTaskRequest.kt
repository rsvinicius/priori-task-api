package com.example.prioritask.model.request

import com.example.prioritask.model.enums.TaskPriorityEnum
import java.time.LocalDate

data class UpdateTaskRequest(
    val title: String?,
    val description: String?,
    val dueDate: LocalDate?,
    val priority: TaskPriorityEnum? = TaskPriorityEnum.NONE,
    val categoryId: Long?
)
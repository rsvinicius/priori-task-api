package com.example.prioritask.model.response

import com.example.prioritask.model.enums.TaskPriorityEnum
import java.time.LocalDate

data class TaskResponse(
    val id: Long,
    val title: String?,
    val description: String?,
    val dueDate: LocalDate?,
    val priority: TaskPriorityEnum? = TaskPriorityEnum.NONE,
    val categoryId: Long?
)
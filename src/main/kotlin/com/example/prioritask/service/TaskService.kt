package com.example.prioritask.service

import com.example.prioritask.exception.UnauthorizedUserException
import com.example.prioritask.model.dto.TaskDto
import com.example.prioritask.model.entity.Task
import com.example.prioritask.repository.TaskRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val categoryService: CategoryService,
    private val accountService: AccountService,
    private val taskRepository: TaskRepository,
    private val objectMapper: ObjectMapper
) {
    fun createTask(taskDto: TaskDto): TaskDto {
        val task = objectMapper.convertValue(taskDto, Task::class.java).apply {
            account = accountService.getAccountById(taskDto.userId)
            category = categoryService.getCategoryById(taskDto.categoryId)
        }

        val savedTask = taskRepository.save(task)

        return convertTaskToTaskDto(savedTask)
    }

    fun listTasks(pageable: Pageable): Page<TaskDto> {
        return taskRepository
            .findAll(pageable)
            .map { task -> convertTaskToTaskDto(task) }
    }

    fun getTaskById(id: Long): TaskDto {
        val task = findTaskById(id)

        return convertTaskToTaskDto(task)
    }

    fun updateTask(id: Long, taskDto: TaskDto): TaskDto {
        val task = findTaskById(id)

        if (task.account.id != taskDto.userId) {
            throw UnauthorizedUserException("This task belongs to another user")
        }

        task.apply {
            title = taskDto.title ?: title
            description = taskDto.description ?: description
            dueDate = taskDto.dueDate ?: dueDate
            priority = taskDto.priority ?: priority
            category = categoryService.getCategoryById(taskDto.categoryId) ?: category
        }

        val savedTask = taskRepository.save(task)

        return convertTaskToTaskDto(savedTask)
    }

    fun deleteTask(id: Long) {
        taskRepository.deleteById(id)
    }

    private fun findTaskById(id: Long): Task =
        taskRepository.findById(id).orElseThrow { EntityNotFoundException() }

    private fun convertTaskToTaskDto(savedTask: Task): TaskDto =
        objectMapper.convertValue(savedTask, TaskDto::class.java)
}
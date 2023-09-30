package com.example.prioritask.service

import com.example.prioritask.exception.UnauthorizedUserException
import com.example.prioritask.model.request.TaskRequest
import com.example.prioritask.model.entity.Task
import com.example.prioritask.model.request.UpdateTaskRequest
import com.example.prioritask.model.response.TaskResponse
import com.example.prioritask.repository.CategoryRepository
import com.example.prioritask.repository.TaskRepository
import com.example.prioritask.repository.UserRepository
import com.example.prioritask.security.UserDetailsImpl
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
@Service
class TaskService(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository,
    private val taskRepository: TaskRepository,
    private val objectMapper: ObjectMapper
) {
    fun createTask(taskRequest: TaskRequest): TaskResponse {
        val task = Task(
            title = taskRequest.title,
            description = taskRequest.description,
            dueDate = taskRequest.dueDate,
            priority = taskRequest.priority,
            user = userRepository.findUserById(getUserId()),
            category = taskRequest.categoryId?.let { categoryRepository.findCategoryById(it) }
        )

        val savedTask = taskRepository.save(task)

        return convertTaskToTaskResponse(savedTask)
    }

    fun listTasks(pageable: Pageable): Page<TaskResponse> {
        return taskRepository
            .findAllByUser(pageable, userRepository.findUserById(getUserId()))
            .map { task -> convertTaskToTaskResponse(task) }
    }

    fun getTaskById(id: Long): TaskResponse {
        val task = findTaskById(id)

        return convertTaskToTaskResponse(task)
    }

    fun updateTask(id: Long, updateTaskRequest: UpdateTaskRequest): TaskResponse {
        val task = findTaskById(id)

        task.apply {
            title = updateTaskRequest.title ?: title
            description = updateTaskRequest.description ?: description
            dueDate = updateTaskRequest.dueDate ?: dueDate
            priority = updateTaskRequest.priority ?: priority
            category = updateTaskRequest.categoryId?.let { categoryRepository.findCategoryById(id) } ?: category
        }

        val savedTask = taskRepository.save(task)

        return convertTaskToTaskResponse(savedTask)
    }

    fun deleteTask(id: Long) {
        val task = findTaskById(id)

        taskRepository.delete(task)
    }

    private fun checkUserIdConsistency(id: Long) {
        if (id != getUserId()) {
            throw UnauthorizedUserException("This task belongs to another user")
        }
    }

    private fun findTaskById(id: Long): Task {
        val task = taskRepository.findById(id).orElseThrow {
            EntityNotFoundException("No Task found with the provided Task id")
        }

        checkUserIdConsistency(task.user.id)

        return task
    }

    private fun convertTaskToTaskResponse(task: Task): TaskResponse =
        objectMapper.convertValue(task, TaskResponse::class.java)

    private fun getUserId(): Long {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        return userDetails.getId()
    }
}


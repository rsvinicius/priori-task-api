package com.example.prioritask.service

import com.example.prioritask.model.entity.Task
import com.example.prioritask.model.request.TaskRequest
import com.example.prioritask.model.request.UpdateTaskRequest
import com.example.prioritask.model.response.TaskResponse
import com.example.prioritask.repository.CategoryRepository
import com.example.prioritask.repository.TaskRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val categoryRepository: CategoryRepository,
    private val userService: UserService,
    private val scheduleService: ScheduleService,
    private val taskRepository: TaskRepository,
    private val objectMapper: ObjectMapper
) {
    fun createTask(taskRequest: TaskRequest): TaskResponse {
        val user = userService.getUserBySecurityContext()

        val task = Task(
            title = taskRequest.title,
            description = taskRequest.description,
            dueDate = taskRequest.dueDate,
            priority = taskRequest.priority,
            user = user,
            category = taskRequest.categoryId?.let { categoryRepository.findCategoryById(it) }
        )

        val savedTask = taskRepository.save(task)

        savedTask.dueDate?.let {
            scheduleService.scheduleTaskReminder(user.id, savedTask.id, it)
        }

        return convertTaskToTaskResponse(savedTask)
    }

    fun listTasks(pageable: Pageable): Page<TaskResponse> {
        return taskRepository
            .findAllByUser(pageable, userService.getUserBySecurityContext())
            .map { task -> convertTaskToTaskResponse(task) }
    }

    fun getTaskById(taskId: Long): TaskResponse {
        val task = findTaskById(taskId)

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

        if (savedTask.dueDate != null && updateTaskRequest.dueDate != null) {
            scheduleService.rescheduleTrigger(id, updateTaskRequest.dueDate)
        }

        return convertTaskToTaskResponse(savedTask)
    }

    fun deleteTask(id: Long) {
        val task = findTaskById(id)

        taskRepository.delete(task)

        scheduleService.deleteJobByTaskId(task.id)
    }

    fun findTaskById(id: Long, userId: Long? = null): Task {
        val task = taskRepository.findById(id).orElseThrow {
            EntityNotFoundException("No task found with the provided Task id")
        }

        userService.checkUserIdConsistency(task.user.id, userId)

        return task
    }

    private fun convertTaskToTaskResponse(task: Task): TaskResponse =
        objectMapper.convertValue(task, TaskResponse::class.java)
}
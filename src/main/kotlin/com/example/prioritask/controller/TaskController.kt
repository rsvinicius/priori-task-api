package com.example.prioritask.controller

import com.example.prioritask.model.request.TaskRequest
import com.example.prioritask.model.request.UpdateTaskRequest
import com.example.prioritask.model.response.TaskResponse
import com.example.prioritask.service.TaskService
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/api/v1/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping
    fun createTask(@Valid @RequestBody taskRequest: TaskRequest): TaskResponse {
        return taskService.createTask(taskRequest)
    }

    @GetMapping
    fun listTasks(@PageableDefault(size = 10) pageable: Pageable): Page<TaskResponse> {
        return taskService.listTasks(pageable)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable @Positive id: Long): TaskResponse {
        return taskService.getTaskById(id)
    }

    @PutMapping("/{id}")
    fun updateTaskById(
        @PathVariable @Positive id: Long,
        @Valid @RequestBody updateTaskRequest: UpdateTaskRequest
    ): TaskResponse {
        return taskService.updateTask(id, updateTaskRequest)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTaskById(@PathVariable @Positive id: Long) {
        taskService.deleteTask(id)
    }
}
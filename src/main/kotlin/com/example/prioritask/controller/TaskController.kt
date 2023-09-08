package com.example.prioritask.controller

import com.example.prioritask.model.dto.TaskDto
import com.example.prioritask.service.TaskService
import com.example.prioritask.model.dto.OnCreate
import com.example.prioritask.model.dto.OnUpdate
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
    @Validated(OnCreate::class)
    fun createTask(@RequestBody taskDto: TaskDto): TaskDto {
        return taskService.createTask(taskDto)
    }

    @GetMapping
    fun listTasks(@PageableDefault(size = 10) pageable: Pageable): Page<TaskDto> {
        return taskService.listTasks(pageable)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable @Positive id: Long): TaskDto {
        return taskService.getTaskById(id)
    }

    @PutMapping("/{id}")
    @Validated(OnUpdate::class)
    fun updateTaskById(
        @PathVariable @Positive id: Long,
        @RequestBody taskDto: TaskDto
    ): TaskDto {
        return taskService.updateTask(id, taskDto)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTaskById(@PathVariable @Positive id: Long) {
        taskService.deleteTask(id)
    }
}
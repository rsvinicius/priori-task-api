package com.example.prioritask.config

import com.example.prioritask.model.exception.FieldErrorDetail
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<List<FieldErrorDetail>> {
        val fieldErrors = ex.bindingResult.fieldErrors.map {
            FieldErrorDetail(it.field, it.defaultMessage ?: "Validation failed")
        }
        return ResponseEntity(fieldErrors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<String> {
        return ResponseEntity("Invalid JSON request", HttpStatus.BAD_REQUEST)
    }
}
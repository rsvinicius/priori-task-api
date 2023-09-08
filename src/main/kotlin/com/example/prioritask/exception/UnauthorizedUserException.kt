package com.example.prioritask.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnauthorizedUserException(message: String) : RuntimeException(message)
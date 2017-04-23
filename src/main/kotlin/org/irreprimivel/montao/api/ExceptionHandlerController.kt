package org.irreprimivel.montao.api

import org.irreprimivel.montao.api.model.Error
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.NoSuchElementException

@RestControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(NOT_FOUND)
    fun notFound(e: NoSuchElementException): Error = Error(NOT_FOUND.value(), e.message!!)
}
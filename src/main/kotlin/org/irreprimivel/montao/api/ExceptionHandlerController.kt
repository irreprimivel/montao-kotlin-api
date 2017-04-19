package org.irreprimivel.montao.api

import org.irreprimivel.montao.api.channel.exception.ChannelNotFoundException
import org.irreprimivel.montao.api.community.exception.CommunityNotFoundException
import org.irreprimivel.montao.api.message.exception.MessageNotFoundException
import org.irreprimivel.montao.api.model.Error
import org.irreprimivel.montao.api.user.exception.UserNotFoundException
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler(CommunityNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun communityNotFound(e: CommunityNotFoundException): Error = Error(NOT_FOUND.value(), e.message!!)

    @ExceptionHandler(ChannelNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun communityNotFound(e: ChannelNotFoundException): Error = Error(NOT_FOUND.value(), e.message!!)

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun communityNotFound(e: UserNotFoundException): Error = Error(NOT_FOUND.value(), e.message!!)

    @ExceptionHandler(MessageNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun communityNotFound(e: MessageNotFoundException): Error = Error(NOT_FOUND.value(), e.message!!)
}
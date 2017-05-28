package org.irreprimivel.montao.api.message.controller

import org.irreprimivel.montao.api.message.entity.Message
import org.irreprimivel.montao.api.message.service.MessageService
import org.irreprimivel.montao.api.util.JsonUtil
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Роутинг:
 * - POST   /messages/
 * - PUT    /messages/
 * - DELETE /messages/
 * - GET    /messages/
 * - GET    /messages/{uuid}
 * - GET    /messages?username={username}
 * - GET    /messages?channelId={channelId}
 */
@RestController
@RequestMapping(value = "/messages")
class MessageRestController(val messageService: MessageService) {
    @PostMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun add(@RequestBody message: Message, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Message> {
        val createdMessage = messageService.add(message)
        val location = uriComponentsBuilder.path("/messages/{uuid}").buildAndExpand(createdMessage.uuid).toUri()
        return ResponseEntity.created(location).body(createdMessage)
    }

    @PutMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun update(@RequestBody message: Message): ResponseEntity<Message> = ResponseEntity
            .ok(messageService.update(message))

    @DeleteMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun delete(@RequestBody message: Message): ResponseEntity<Message> {
        messageService.delete(message)
        return ResponseEntity.ok(message)
    }

    @GetMapping(produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findAll(@RequestParam(defaultValue = "1") page: Int,
                @RequestParam(defaultValue = "30") limit: Int,
                @RequestParam(required = false) fields: Array<out String>?): ResponseEntity<String> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", messageService.totalCount().toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        val messages = messageService.findAll(page, limit)
        return ResponseEntity.ok().headers(headers).body(JsonUtil.objectToJsonString(fields, "message", messages))
    }

    @GetMapping(value = "/{uuid}", produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findByUuid(@PathVariable uuid: String,
                   @RequestParam(required = false) fields: Array<out String>?): ResponseEntity<String> {

        val message = messageService.findByUuid(uuid)
        return ResponseEntity.ok(JsonUtil.objectToJsonString(fields, "message", message))
    }

    @GetMapping(produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findAllByUsername(@RequestParam username: String,
                          @RequestParam(defaultValue = "1") page: Int,
                          @RequestParam(defaultValue = "30") limit: Int,
                          @RequestParam(required = false) fields: Array<out String>?): ResponseEntity<String> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", messageService.countByUsername(username).toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        val messages = messageService.findAllByUsername(username, page, limit)
        return ResponseEntity.ok().headers(headers).body(JsonUtil.objectToJsonString(fields, "message", messages))
    }

    @GetMapping(produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findAllByChannel(@RequestParam channelId: Long,
                         @RequestParam(defaultValue = "1") page: Int,
                         @RequestParam(defaultValue = "30") limit: Int,
                         @RequestParam(required = false) fields: Array<out String>?): ResponseEntity<String> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", messageService.countByChannelId(channelId).toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        val messages = messageService.findAllByChannelId(channelId, page, limit)
        return ResponseEntity.ok().headers(headers).body(JsonUtil.objectToJsonString(fields, "message", messages))
    }
}
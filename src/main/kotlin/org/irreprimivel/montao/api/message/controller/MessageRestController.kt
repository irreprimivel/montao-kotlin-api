package org.irreprimivel.montao.api.message.controller

import org.irreprimivel.montao.api.message.entity.Message
import org.irreprimivel.montao.api.message.service.MessageService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.*
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
*/
@RestController
@RequestMapping(value = "/messages")
class MessageRestController(val messageService: MessageService) {
    @PostMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun add(@RequestBody message: Message, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Message> {
        messageService.add(message)
        val location = uriComponentsBuilder.path("/messages/{uuid}").buildAndExpand(message.uuid).toUri()
        return ResponseEntity.created(location).build()
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
    fun findAll(@RequestParam page: Int = 1, @RequestParam limit: Int = 30): ResponseEntity<List<Message>> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", messageService.totalCount().toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        return ResponseEntity.ok().headers(headers).body(messageService.findAll(page, limit))
    }

    @GetMapping(value = "/{uuid}", produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findByUuid(@PathVariable uuid: String): ResponseEntity<Message> = ResponseEntity
            .ok(messageService.findByUuid(uuid))
}
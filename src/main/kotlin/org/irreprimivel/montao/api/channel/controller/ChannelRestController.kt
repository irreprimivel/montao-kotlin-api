package org.irreprimivel.montao.api.channel.controller

import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.channel.service.ChannelService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Рестовый котроллерб который занимается выдачей данных по каналам.
 * Роутинг:
 * - POST   /channels/
 * - PUT    /channels/
 * - DELETE /channels/
 * - GET    /channels/
 * - GET    /channels/{title}
 * - GET    /channels/{title}/messages
 */
@RestController
@RequestMapping(value = "/channels")
class ChannelRestController(val channelService: ChannelService) {
    @PostMapping(produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun add(@RequestBody channel: Channel, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Channel> {
        channelService.add(channel)
        val location = uriComponentsBuilder.path("/channels/{title}").buildAndExpand(channel.title).toUri()
        return ResponseEntity.created(location).build()
    }

    @PutMapping(
            consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun update(@RequestBody channel: Channel): ResponseEntity<Channel> = ResponseEntity
            .ok(channelService.update(channel))

    @DeleteMapping(
            consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun delete(@RequestBody channel: Channel): ResponseEntity<Channel> {
        channelService.delete(channel)
        return ResponseEntity.ok(channel)
    }

    @GetMapping(produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun getAll(page: Int = 1, limit: Int = 30): ResponseEntity<List<Channel>> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", channelService.totalCount().toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        return ResponseEntity.ok(channelService.getAll(page, limit))
    }

    @GetMapping(value = "/{title}", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun getByTitle(@PathVariable title: String): ResponseEntity<Channel> = ResponseEntity
            .ok(channelService.getByTitle(title))


}
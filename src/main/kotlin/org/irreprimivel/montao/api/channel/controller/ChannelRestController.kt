package org.irreprimivel.montao.api.channel.controller

import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.channel.service.ChannelService
import org.irreprimivel.montao.api.util.JsonUtil
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
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
 * - GET    /channels?community={communityTitle}
 * - GET    /channels/{id}/messages
 * - HEAD   /channels?title={title}&community={title}
 */
@RestController
@RequestMapping(value = "/channels")
class ChannelRestController(val channelService: ChannelService) {
    @PostMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun add(@RequestBody channel: Channel, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Channel> {
        val createdChannel = channelService.add(channel)
        val location = uriComponentsBuilder.path("/channels/{title}").buildAndExpand(createdChannel.title).toUri()
        return ResponseEntity.created(location).body(createdChannel)
    }

    @PutMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun update(@RequestBody channel: Channel): ResponseEntity<Channel> = ResponseEntity.ok(channelService.update(channel))

    @DeleteMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun delete(@RequestBody channel: Channel): ResponseEntity<Channel> {
        channelService.delete(channel)
        return ResponseEntity.ok(channel)
    }

    @GetMapping(produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findAll(@RequestParam(defaultValue = "1") page: Int,
                @RequestParam(defaultValue = "30") limit: Int,
                @RequestParam(required = false) fields: Array<out String>?): ResponseEntity<String> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", channelService.totalCount().toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        val channels = channelService.findAll(page, limit)
        return ResponseEntity.ok().headers(headers).body(JsonUtil.objectToJsonString(fields, "channel", channels))
    }

    /**
     * Возвращает все каналы в сообществе.
     */
    @GetMapping(produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findByCommunity(@RequestParam community: String,
                        @RequestParam(defaultValue = "1") page: Int,
                        @RequestParam(defaultValue = "30") limit: Int,
                        @RequestParam(required = false) fields: Array<out String>?): ResponseEntity<String> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", channelService.countByCommunity(community).toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        val channels = channelService.findByCommunity(community, page, limit)
        return ResponseEntity.ok().headers(headers).body(JsonUtil.objectToJsonString(fields, "channel", channels))
    }

    //    @GetMapping(value = "/{id}/messages", produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    //    fun findMessagesByChannel(@PathVariable id: Long,
    //                              @RequestParam page: Int,
    //                              @RequestParam limit: Int): ResponseEntity<List<Message>> {
    //        val headers = HttpHeaders()
    //        with(headers) {
    //            set("X-Pagination-Count", channelService.findById(id).messages.stream().count().toString())
    //            set("X-Pagination-Page", page.toString())
    //            set("X-Pagination-Limit", limit.toString())
    //        }
    //        return ResponseEntity.ok().headers(headers).body(channelService.findById(id)
    //                                                                 .messages.stream()
    //                                                                 .skip(((page - 1) * limit).toLong())
    //                                                                 .limit(limit.toLong())
    //                                                                 .toList())
    //    }
}
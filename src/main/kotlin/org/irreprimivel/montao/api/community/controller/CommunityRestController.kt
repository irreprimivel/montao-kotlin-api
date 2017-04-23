package org.irreprimivel.montao.api.community.controller

import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.community.service.CommunityService
import org.irreprimivel.montao.api.subscription.SubscriptionService
import org.irreprimivel.montao.api.user.User
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.HEAD
import org.springframework.web.util.UriComponentsBuilder
import java.util.NoSuchElementException

/**
 * Роутинг:
 * - POST   /communities/
 * - PUT    /communities/
 * - DELETE /communities/
 * - GET    /communities/
 * - GET    /communities/{title}
 * - GET    /communities/{title}/users
 * - GET    /communities/{title}/channels
 * - HEAD   /communities/{title}/channels?channel={channel}
 * - HEAD   /communities?title={title}
 */
@RestController
@RequestMapping(value = "/communities")
class CommunityRestController(val communityService: CommunityService, val subscriptionService: SubscriptionService) {
    @PostMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun add(@RequestBody community: Community, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Community> {
        communityService.add(community)
        val location = uriComponentsBuilder.path("/communities/{title}").buildAndExpand(community.title).toUri()
        return ResponseEntity.created(location).build()
    }

    @PutMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun update(@RequestBody community: Community): ResponseEntity<Community> = ResponseEntity
            .ok(communityService.update(community))

    @DeleteMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun delete(@RequestBody community: Community): ResponseEntity<Community> {
        communityService.delete(community)
        return ResponseEntity.ok(community)
    }

    @GetMapping(produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findAll(@RequestParam page: Int, @RequestParam limit: Int): ResponseEntity<List<Community>> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", communityService.totalCount().toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        return ResponseEntity.ok().headers(headers).body(communityService.findAll(page, limit))
    }

    @GetMapping(value = "/{title}", produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findByTitle(@PathVariable title: String): ResponseEntity<Community> = ResponseEntity
            .ok(communityService.findByTitle(title))

    @GetMapping(value = "/{title}/users", produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findUsersByCommunity(@PathVariable title: String,
                             @RequestParam page: Int,
                             @RequestParam limit: Int): ResponseEntity<List<User>> = ResponseEntity
            .ok(subscriptionService.findByCommunity(communityService.findByTitle(title), page, limit))

    @GetMapping(value = "/{title}/channels", produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findChannelsByCommunity(@PathVariable title: String): ResponseEntity<List<Channel>> = ResponseEntity
            .ok(communityService.findByTitle(title).channels)

    @RequestMapping(value = "/{title}/channels/", params = arrayOf("channel"), method = arrayOf(HEAD))
    fun checkChannelTitle(@PathVariable title: String,
                          @RequestParam channel: String): ResponseEntity<*> = ResponseEntity
            .ok(communityService.findByTitle(title)
                        .channels!!.stream()
                        .filter { it.equals(channel) }
                        .findFirst()
                        .orElseThrow { NoSuchElementException("Channel not found") })

    @RequestMapping(params = arrayOf("title"), method = arrayOf(HEAD))
    fun checkTitle(@RequestParam title: String): ResponseEntity<*> = ResponseEntity
            .ok(communityService.findByTitle(title))
}
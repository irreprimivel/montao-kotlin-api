package org.irreprimivel.montao.api.community.controller

import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.community.service.CommunityService
import org.irreprimivel.montao.api.subscription.SubscriptionService
import org.irreprimivel.montao.api.user.User
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Роутинг:
 * - POST   /communities/
 * - PUT    /communities/
 * - DELETE /communities/
 * - GET    /communities/
 * - GET    /communities/{title}
 * - GET    /communities/{title}/users
 * - GET    /communities/{title}/channels
 */
@RestController
@RequestMapping(value = "/communities")
class CommunityRestController(val communityService: CommunityService, val subscriptionService: SubscriptionService) {
    @PostMapping(
            consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun add(@RequestBody community: Community, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Community> {
        communityService.add(community)
        val location = uriComponentsBuilder.path("/communities/{title}").buildAndExpand(community.title).toUri()
        return ResponseEntity.created(location).build()
    }

    @PutMapping(
            consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun update(@RequestBody community: Community): ResponseEntity<Community> = ResponseEntity
            .ok(communityService.update(community))

    @DeleteMapping(
            consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun delete(@RequestBody community: Community): ResponseEntity<Community> {
        communityService.delete(community)
        return ResponseEntity.ok(community)
    }

    @GetMapping(produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun getAll(page: Int = 1, limit: Int = 30): ResponseEntity<List<Community>> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", communityService.totalCount().toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        return ResponseEntity.ok(communityService.getAll(page, limit))
    }

    @GetMapping(value = "/{title}", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun getByTitle(@PathVariable title: String): ResponseEntity<Community> = ResponseEntity
            .ok(communityService.getByTitle(title))

    @GetMapping(value = "/{title}/users", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun getUsersByCommunity(@PathVariable title: String, page: Int = 1, limit: Int = 30): ResponseEntity<List<User>> =
            ResponseEntity.ok(subscriptionService.getByCommunity(communityService.getByTitle(title), page, limit))

    @GetMapping(value = "/{title}/channels", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun getChannelsByCommunity(@PathVariable title: String): ResponseEntity<List<Channel>> {

    }
}
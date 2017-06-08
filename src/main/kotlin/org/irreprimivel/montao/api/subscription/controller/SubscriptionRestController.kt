package org.irreprimivel.montao.api.subscription.controller

import org.irreprimivel.montao.api.community.service.CommunityService
import org.irreprimivel.montao.api.subscription.entity.Subscription
import org.irreprimivel.montao.api.subscription.service.SubscriptionService
import org.irreprimivel.montao.api.user.service.UserService
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping(value = "/subscriptions")
class SubscriptionRestController(val subscriptionService: SubscriptionService,
                                 val communityService: CommunityService,
                                 val userService: UserService) {
    @PostMapping(params = arrayOf("cid"), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun join(@RequestParam(name = "cid") communityId: Long, principal: Principal): ResponseEntity<Subscription> {
        val community = communityService.findById(communityId)
        val user = userService.findByUsername(principal.name)
        return ResponseEntity.ok(subscriptionService.add(Subscription(null, community, user)))
    }

    @DeleteMapping(params = arrayOf("cid"), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun leave(@RequestParam(name = "cid") communityId: Long, principal: Principal): ResponseEntity<Subscription> {
        val community = communityService.findById(communityId)
        val user = userService.findByUsername(principal.name)
        val subscription = subscriptionService.find(community, user)
        subscriptionService.delete(subscription)
        return ResponseEntity.ok(subscription)
    }
}
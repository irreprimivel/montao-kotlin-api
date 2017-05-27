package org.irreprimivel.montao.api.subscription.service

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.subscription.entity.Subscription
import org.irreprimivel.montao.api.user.User

interface SubscriptionService {
    fun add(subscription: Subscription): Subscription
    fun delete(subscription: Subscription)
    fun find(community: Community, user: User): Subscription
    fun findByUser(user: User, page: Int, limit: Int): List<Community>
    fun findByCommunity(community: Community, page: Int, limit: Int): List<User>
}
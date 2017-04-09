package org.irreprimivel.montao.api.subscription

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.user.User

interface SubscriptionService {
    fun add(subscription: Subscription)
    fun delete(subscription: Subscription)
    fun getByUser(user: User, page: Int, limit: Int): List<Community>
    fun getByCommunity(community: Community, page: Int, limit: Int): List<User>
}
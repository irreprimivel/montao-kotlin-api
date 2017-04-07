package org.unstoppable.montao.backend.subscription

import org.unstoppable.montao.backend.community.entity.Community
import org.unstoppable.montao.backend.user.User

interface SubscriptionService {
    fun add(subscription: Subscription)
    fun delete(subscription: Subscription)
    fun getByUser(user: User, page: Int, limit: Int): List<Community>
    fun getByCommunity(community: Community, page: Int, limit: Int): List<User>
}
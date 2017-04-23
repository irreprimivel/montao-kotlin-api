package org.irreprimivel.montao.api.subscription

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.user.User

interface SubscriptionDAO {
    fun add(subscription: Subscription)
    fun delete(subscription: Subscription)
    fun findByUser(user: User, page: Int, limit: Int): List<Community>
    fun findByCommunity(community: Community, page: Int, limit: Int): List<User>
    fun communitiesCountByUser(user: User): Long
    fun usersCountByCommunity(community: Community): Long
}
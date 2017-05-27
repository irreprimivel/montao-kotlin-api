package org.irreprimivel.montao.api.subscription.dao

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.subscription.entity.Subscription
import org.irreprimivel.montao.api.user.User

interface SubscriptionDAO {
    fun add(subscription: Subscription): Subscription
    fun delete(subscription: Subscription)
    fun find(community: Community, user: User): Subscription
    fun findByUser(user: User, page: Int, limit: Int): List<Community>
    fun findByCommunity(community: Community, page: Int, limit: Int): List<User>
    fun communitiesCountByUser(user: User): Long
    fun usersCountByCommunity(community: Community): Long
}
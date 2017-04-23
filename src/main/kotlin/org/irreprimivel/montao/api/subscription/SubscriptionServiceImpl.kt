package org.irreprimivel.montao.api.subscription

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.user.User
import org.springframework.stereotype.Service

@Service
class SubscriptionServiceImpl(val subscriptionDAO: SubscriptionDAO) : SubscriptionService {
    override fun add(subscription: Subscription) = subscriptionDAO.add(subscription)

    override fun delete(subscription: Subscription) = subscriptionDAO.delete(subscription)

    override fun findByUser(user: User, page: Int, limit: Int): List<Community> = subscriptionDAO
            .findByUser(user, page, limit)

    override fun findByCommunity(community: Community, page: Int, limit: Int): List<User> = subscriptionDAO
            .findByCommunity(community, page, limit)
}
package org.unstoppable.montao.backend.subscription

import org.unstoppable.montao.backend.community.entity.Community
import org.unstoppable.montao.backend.user.User

class SubscriptionServiceImpl(val subscriptionDAO: SubscriptionDAO) : SubscriptionService {
    override fun add(subscription: Subscription) {
        subscriptionDAO.add(subscription)
    }

    override fun delete(subscription: Subscription) {
        subscriptionDAO.delete(subscription)
    }

    override fun getByUser(user: User, page: Int, limit: Int): List<Community> {
        return subscriptionDAO.getByUser(user, page, limit)
    }

    override fun getByCommunity(community: Community, page: Int, limit: Int): List<User> {
        return subscriptionDAO.getByCommunity(community, page, limit)
    }
}
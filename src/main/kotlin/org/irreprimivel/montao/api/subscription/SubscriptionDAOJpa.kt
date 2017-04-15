package org.irreprimivel.montao.api.subscription

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.user.User
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class SubscriptionDAOJpa(@PersistenceContext val entityManager: EntityManager) : SubscriptionDAO {
    override fun add(subscription: Subscription) = entityManager.persist(subscription)

    override fun delete(subscription: Subscription) = entityManager.remove(subscription)

    override fun getByUser(user: User, page: Int, limit: Int): List<Community> = entityManager
            .createQuery("select community from Subscription where user = :user", Community::class.java)
            .setParameter("user", user)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun getByCommunity(community: Community, page: Int, limit: Int): List<User> = entityManager
            .createQuery("select user from Subscription where community = :community", User::class.java)
            .setParameter("community", community)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun communitiesCountByUser(user: User): Long = entityManager
            .createQuery("select count(id) from Subscription where user = :user", Community::class.java)
            .setParameter("user", user)
            .singleResult as Long

    override fun usersCountByCommunity(community: Community): Long = entityManager
            .createQuery("select count(id) from Subscription where community = :community", User::class.java)
            .setParameter("community", community)
            .singleResult as Long
}
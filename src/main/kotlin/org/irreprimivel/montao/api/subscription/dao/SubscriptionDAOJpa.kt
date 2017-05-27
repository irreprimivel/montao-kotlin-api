package org.irreprimivel.montao.api.subscription.dao

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.subscription.entity.Subscription
import org.irreprimivel.montao.api.user.User
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManagerFactory

@Repository
@Transactional
class SubscriptionDAOJpa(entityManagerFactory: EntityManagerFactory) : SubscriptionDAO {
    private val entityManager = entityManagerFactory.createEntityManager()

    override fun add(subscription: Subscription): Subscription {
        entityManager.run {
            persist(subscription)
            flush()
        }
        return subscription
    }

    override fun delete(subscription: Subscription) = entityManager.remove(subscription)

    override fun find(community: Community, user: User): Subscription = entityManager
            .createQuery("select s from Subscription s where s.community = :community and s.user = :user",
                         Subscription::class.java)
            .setParameter("community", community)
            .setParameter("user", user)
            .resultList.stream()
            .findFirst()
            .orElseThrow { NoSuchElementException("Subscription not found") }

    override fun findByUser(user: User, page: Int, limit: Int): List<Community> = entityManager
            .createQuery("select s.community from Subscription s where s.user = :user", Community::class.java)
            .setParameter("user", user)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun findByCommunity(community: Community, page: Int, limit: Int): List<User> = entityManager
            .createQuery("select s.user from Subscription s where s.community = :community", User::class.java)
            .setParameter("community", community)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun communitiesCountByUser(user: User): Long = entityManager
            .createQuery("select count(s.id) from Subscription s where s.user = :user")
            .setParameter("user", user)
            .singleResult as Long

    override fun usersCountByCommunity(community: Community): Long = entityManager
            .createQuery("select count(s.id) from Subscription s where s.community = :community")
            .setParameter("community", community)
            .singleResult as Long
}
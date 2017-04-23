package org.irreprimivel.montao.api.subscription

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.user.User
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceContext

@Repository
class SubscriptionDAOJpa(val entityManagerFactory: EntityManagerFactory) : SubscriptionDAO {
    override fun add(subscription: Subscription) = entityManagerFactory.createEntityManager().persist(subscription)

    override fun delete(subscription: Subscription) = entityManagerFactory.createEntityManager().remove(subscription)

    override fun findByUser(user: User, page: Int, limit: Int): List<Community> = entityManagerFactory.createEntityManager()
            .createQuery("select s.community from Subscription s where s.user = :user", Community::class.java)
            .setParameter("user", user)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun findByCommunity(community: Community, page: Int, limit: Int): List<User> = entityManagerFactory.createEntityManager()
            .createQuery("select s.user from Subscription s where s.community = :community", User::class.java)
            .setParameter("community", community)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun communitiesCountByUser(user: User): Long = entityManagerFactory.createEntityManager()
            .createQuery("select count(s.id) from Subscription s where s.user = :user")
            .setParameter("user", user)
            .singleResult as Long

    override fun usersCountByCommunity(community: Community): Long = entityManagerFactory.createEntityManager()
            .createQuery("select count(s.id) from Subscription s where s.community = :community")
            .setParameter("community", community)
            .singleResult as Long
}
package org.irreprimivel.montao.api.channel.dao

import org.irreprimivel.montao.api.channel.entity.Channel
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException
import javax.persistence.EntityManagerFactory

@Repository
@Transactional
class ChannelDAOJpa(entityManagerFactory: EntityManagerFactory) : ChannelDAO {
    private val entityManager = entityManagerFactory.createEntityManager()

    override fun add(channel: Channel): Channel {
        entityManager.run {
            persist(channel)
            flush()
        }
        return channel
    }

    override fun delete(channel: Channel) = entityManager.remove(channel)

    override fun update(channel: Channel): Channel = entityManager.merge(channel)

    override fun findAll(page: Int, limit: Int): List<Channel> = entityManager
            .createQuery("select c from Channel c", Channel::class.java)
            .setMaxResults(limit)
            .setFirstResult((page - 1) * limit)
            .resultList

    override fun findByCommunity(communityTitle: String, page: Int, limit: Int): List<Channel> = entityManager
            .createQuery("select c from Channel c where c.community.title = :title", Channel::class.java)
            .setParameter("title", communityTitle)
            .setMaxResults(limit)
            .setFirstResult((page - 1) * limit)
            .resultList

    override fun findById(id: Long): Channel = entityManager
            .createQuery("select c from Channel c where c.id = :id", Channel::class.java)
            .setParameter("id", id)
            .resultList.stream()
            .findFirst()
            .orElseThrow { NoSuchElementException("Channel with $id id not found") }

    override fun totalCount(): Long = entityManager
            .createQuery("select count(c.id) from Channel c")
            .singleResult as Long

    override fun countByCommunity(communityTitle: String): Long = entityManager
            .createQuery("select count(c.id) from Channel c where c.community.title = :title")
            .setParameter("title", communityTitle)
            .singleResult as Long
}
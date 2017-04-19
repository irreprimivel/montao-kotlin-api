package org.irreprimivel.montao.api.channel.dao

import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.channel.exception.ChannelNotFoundException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class ChannelDAOJpa(@PersistenceContext val entityManager: EntityManager) : ChannelDAO {
    override fun getAll(page: Int, limit: Int): List<Channel> = entityManager
            .createQuery("select Channel from Channel", Channel::class.java)
            .setMaxResults(limit)
            .setFirstResult((page - 1) * limit)
            .resultList

    override fun getByTitle(title: String): Channel = entityManager
            .createQuery("select Channel from Channel where title = :title", Channel::class.java)
            .setParameter("title", title)
            .resultList
            .stream()
            .findFirst()
            .orElseThrow { ChannelNotFoundException("Channel with $title title not found") }

    override fun add(channel: Channel) = entityManager.persist(channel)

    override fun delete(channel: Channel) = entityManager.remove(channel)

    override fun update(channel: Channel): Channel = entityManager.merge(channel)

    override fun totalCount(): Long = entityManager.createQuery("select id from Channel", Channel::class.java)
            .resultList.stream()
            .count()
}
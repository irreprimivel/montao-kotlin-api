package org.irreprimivel.montao.api.message.dao

import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.message.entity.Message
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException
import javax.persistence.EntityManagerFactory

@Repository
@Transactional
class MessageDAOJpa(val entityManagerFactory: EntityManagerFactory) : MessageDAO {
    override fun add(message: Message) = entityManagerFactory.createEntityManager().persist(message)

    override fun delete(message: Message) = entityManagerFactory.createEntityManager().remove(message)

    override fun update(message: Message): Message = entityManagerFactory.createEntityManager().merge(message)

    override fun findAll(page: Int, limit: Int): List<Message> = entityManagerFactory.createEntityManager()
            .createQuery("select m from Message m", Message::class.java)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun findAllByChannel(channel: Channel, page: Int, limit: Int): List<Message> = entityManagerFactory.createEntityManager()
            .createQuery("select m from Message m where m.channel = :channel", Message::class.java)
            .setParameter("channel", channel)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun findByUuid(uuid: String): Message = entityManagerFactory.createEntityManager()
            .createQuery("select m from Message m where m.uuid = :uuid", Message::class.java)
            .setParameter("uuid", uuid)
            .resultList.stream()
            .findFirst()
            .orElseThrow { NoSuchElementException("Message with $uuid uuid not found") }

    override fun totalCount(): Long = entityManagerFactory.createEntityManager()
            .createQuery("select count(m.id) from Message m")
            .singleResult as Long

    override fun countByChannel(title: String): Long = entityManagerFactory.createEntityManager()
            .createQuery("select count(m.id) from Message m where m.channel.title = :title")
            .setParameter("title", title)
            .singleResult as Long
}
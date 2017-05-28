package org.irreprimivel.montao.api.message.dao

import org.irreprimivel.montao.api.message.entity.Message
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException
import javax.persistence.EntityManagerFactory

@Repository
@Transactional
class MessageDAOJpa(entityManagerFactory: EntityManagerFactory) : MessageDAO {
    private val entityManager = entityManagerFactory.createEntityManager()

    override fun add(message: Message): Message {
        entityManager.run {
            persist(message)
            flush()
        }
        return message
    }

    override fun delete(message: Message) = entityManager.remove(message)

    override fun update(message: Message): Message = entityManager.merge(message)

    override fun findAll(page: Int, limit: Int): List<Message> = entityManager
            .createQuery("select m from Message m", Message::class.java)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun findAllByChannelId(channelId: Long, page: Int, limit: Int): List<Message> = entityManager
            .createQuery("select m from Message m where m.channel.id = :channelId", Message::class.java)
            .setParameter("channelId", channelId)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun findAllByUsername(username: String, page: Int, limit: Int): List<Message> = entityManager
            .createQuery("select m from Message m where m.user.username = :username", Message::class.java)
            .setParameter("username", username)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun findByUuid(uuid: String): Message = entityManager
            .createQuery("select m from Message m where m.uuid = :uuid", Message::class.java)
            .setParameter("uuid", uuid)
            .resultList.stream()
            .findFirst()
            .orElseThrow { NoSuchElementException("Message with $uuid uuid not found") }

    override fun totalCount(): Long = entityManager
            .createQuery("select count(m.id) from Message m")
            .singleResult as Long

    override fun countByChannelId(channelId: Long): Long = entityManager
            .createQuery("select count(m.id) from Message m where m.channel.id = :channelId")
            .setParameter("channelId", channelId)
            .singleResult as Long

    override fun countByUsername(username: String): Long = entityManager
            .createQuery("select count(m.id) from Message m where m.user.username = :username")
            .setParameter("username", username)
            .singleResult as Long
}

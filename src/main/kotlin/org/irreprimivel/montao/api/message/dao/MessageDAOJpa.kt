package org.irreprimivel.montao.api.message.dao

import org.irreprimivel.montao.api.message.entity.Message
import org.irreprimivel.montao.api.message.exception.MessageNotFoundException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class MessageDAOJpa(@PersistenceContext val entityManager: EntityManager) : MessageDAO {
    override fun getAll(page: Int, limit: Int): List<Message> = entityManager
            .createQuery("select Message from Message", Message::class.java)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun getAllByChannel(title: String, page: Int, limit: Int): List<Message> = entityManager
            .createQuery("select Message from Message where channel.title = :title", Message::class.java)
            .setParameter("title", title)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .resultList

    override fun getByUuid(uuid: String): Message = entityManager
            .createQuery("select Message from Message where uuid = :uuid", Message::class.java)
            .setParameter("uuid", uuid)
            .resultList.stream()
            .findFirst()
            .orElseThrow { MessageNotFoundException("Message with $uuid uuid not found") }

    override fun add(message: Message) = entityManager.persist(message)

    override fun delete(message: Message) = entityManager.remove(message)

    override fun update(message: Message): Message = entityManager.merge(message)

    override fun totalCount(): Long = entityManager.createQuery("select id from Message", Message::class.java)
            .resultList.stream()
            .count()

    override fun countByChannel(title: String): Long = entityManager
            .createQuery("select id from Message where channel.title = :title", Message::class.java)
            .setParameter("title", title)
            .resultList.stream()
            .count()
}
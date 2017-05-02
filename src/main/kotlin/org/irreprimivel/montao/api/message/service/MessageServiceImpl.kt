package org.irreprimivel.montao.api.message.service

import org.irreprimivel.montao.api.message.dao.MessageDAO
import org.irreprimivel.montao.api.message.entity.Message
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(val messageDAO: MessageDAO) : MessageService {
    override fun findAll(page: Int, limit: Int): List<Message> = messageDAO.findAll(page, limit)

    override fun findAllByChannelId(channelId: Long, page: Int, limit: Int): List<Message> = messageDAO
            .findAllByChannelId(channelId, page, limit)

    override fun findAllByUsername(username: String, page: Int, limit: Int): List<Message> = messageDAO
            .findAllByUsername(username, page, limit)

    override fun findByUuid(uuid: String): Message = messageDAO.findByUuid(uuid)

    override fun add(message: Message) = messageDAO.add(message)

    override fun delete(message: Message) = messageDAO.delete(message)

    override fun update(message: Message): Message = messageDAO.update(message)

    override fun totalCount(): Long = messageDAO.totalCount()

    override fun countByChannelId(channelId: Long): Long = messageDAO.countByChannelId(channelId)

    override fun countByUsername(username: String): Long = messageDAO.countByUsername(username)
}
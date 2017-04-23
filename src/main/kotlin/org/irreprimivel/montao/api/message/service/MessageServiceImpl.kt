package org.irreprimivel.montao.api.message.service

import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.message.dao.MessageDAO
import org.irreprimivel.montao.api.message.entity.Message
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(val messageDAO: MessageDAO) : MessageService {
    override fun findAll(page: Int, limit: Int): List<Message> = messageDAO.findAll(page, limit)

    override fun findAllByChannel(channel: Channel, page: Int, limit: Int): List<Message> = messageDAO
            .findAllByChannel(channel, page, limit)

    override fun findByUuid(uuid: String): Message = messageDAO.findByUuid(uuid)

    override fun add(message: Message) = messageDAO.add(message)

    override fun delete(message: Message) = messageDAO.delete(message)

    override fun update(message: Message): Message = messageDAO.update(message)

    override fun totalCount(): Long = messageDAO.totalCount()

    override fun countByChannel(title: String): Long = messageDAO.countByChannel(title)
}
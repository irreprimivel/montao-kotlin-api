package org.irreprimivel.montao.api.message.service

import org.irreprimivel.montao.api.message.dao.MessageDAO
import org.irreprimivel.montao.api.message.entity.Message
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(val messageDAO: MessageDAO) : MessageService {
    override fun getAll(page: Int, limit: Int): List<Message> = messageDAO.getAll(page, limit)

    override fun getAllByChannel(title: String, page: Int, limit: Int): List<Message> = messageDAO
            .getAllByChannel(title, page, limit)

    override fun getByUuid(uuid: String): Message = messageDAO.getByUuid(uuid)

    override fun add(message: Message) = messageDAO.add(message)

    override fun delete(message: Message) = messageDAO.delete(message)

    override fun update(message: Message): Message = messageDAO.update(message)

    override fun totalCount(): Long = messageDAO.totalCount()

    override fun countByChannel(title: String): Long = messageDAO.countByChannel(title)
}
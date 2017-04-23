package org.irreprimivel.montao.api.message.dao

import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.message.entity.Message

interface MessageDAO {
    fun findAll(page: Int, limit: Int): List<Message>
    fun findAllByChannel(channel: Channel, page: Int, limit: Int): List<Message>
    fun findByUuid(uuid: String): Message
    fun add(message: Message)
    fun delete(message: Message)
    fun update(message: Message): Message
    fun totalCount(): Long
    fun countByChannel(title: String): Long
}
package org.irreprimivel.montao.api.message.dao

import org.irreprimivel.montao.api.message.entity.Message

interface MessageDAO {
    fun findAll(page: Int, limit: Int): List<Message>
    fun findAllByChannelId(channelId: Long, page: Int, limit: Int): List<Message>
    fun findAllByUsername(username: String, page: Int, limit: Int): List<Message>
    fun findByUuid(uuid: String): Message
    fun add(message: Message): Message
    fun delete(message: Message)
    fun update(message: Message): Message
    fun totalCount(): Long
    fun countByChannelId(channelId: Long): Long
    fun countByUsername(username: String): Long
}
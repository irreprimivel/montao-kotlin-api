package org.irreprimivel.montao.api.message.dao

import org.irreprimivel.montao.api.message.entity.Message

interface MessageDAO {
    fun getAll(page: Int, limit: Int): List<Message>
    fun getAllByChannel(title: String, page: Int, limit: Int): List<Message>
    fun getByUuid(uuid: String): Message
    fun add(message: Message)
    fun delete(message: Message)
    fun update(message: Message): Message
    fun totalCount(): Long
    fun countByChannel(title: String): Long
}
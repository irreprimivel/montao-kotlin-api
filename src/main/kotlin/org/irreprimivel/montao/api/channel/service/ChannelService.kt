package org.irreprimivel.montao.api.channel.service

import org.irreprimivel.montao.api.channel.entity.Channel

interface ChannelService {
    fun findAll(page: Int, limit: Int): List<Channel>
    fun findByTitle(title: String): Channel
    fun findById(id: Long): Channel
    fun add(channel: Channel)
    fun delete(channel: Channel)
    fun update(channel: Channel): Channel
    fun totalCount(): Long
}
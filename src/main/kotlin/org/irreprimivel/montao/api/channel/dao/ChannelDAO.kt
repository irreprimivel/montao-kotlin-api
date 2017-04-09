package org.irreprimivel.montao.api.channel.dao

import org.irreprimivel.montao.api.channel.entity.Channel

interface ChannelDAO {
    fun getAll(page: Int, limit: Int): List<Channel>
    fun getByTitle(title: String): Channel
    fun add(channel: Channel)
    fun delete(channel: Channel)
    fun update(channel: Channel): Channel
    fun totalCount(): Long
}
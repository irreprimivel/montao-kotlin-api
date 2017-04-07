package org.unstoppable.montao.backend.channel.dao

import java.nio.channels.Channel

interface ChannelDAO {
    fun getAll(page: Int, limit: Int): List<Channel>
    fun getByTitle(title: String): Channel
    fun add(channel: Channel)
    fun delete(channel: Channel)
    fun update(channel: Channel): Channel
    fun totalCount(): Long
}
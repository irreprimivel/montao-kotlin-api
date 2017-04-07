package org.unstoppable.montao.backend.channel.service

import java.nio.channels.Channel

interface ChannelService {
    fun getAll(page: Int, limit: Int): List<Channel>
    fun getByTitle(title: String): Channel
    fun add(channel: Channel)
    fun delete(channel: Channel)
    fun update(channel: Channel): Channel
    fun totalCount(): Long
}
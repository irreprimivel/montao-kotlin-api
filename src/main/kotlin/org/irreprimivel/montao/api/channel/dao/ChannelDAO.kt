package org.irreprimivel.montao.api.channel.dao

import org.irreprimivel.montao.api.channel.entity.Channel

interface ChannelDAO {
    fun add(channel: Channel)
    fun delete(channel: Channel)
    fun update(channel: Channel): Channel
    fun findAll(page: Int, limit: Int): List<Channel>
    fun findByCommunity(communityTitle: String, page: Int, limit: Int): List<Channel>
    fun findById(id: Long): Channel
    fun totalCount(): Long
    fun countByCommunity(communityTitle: String): Long
}
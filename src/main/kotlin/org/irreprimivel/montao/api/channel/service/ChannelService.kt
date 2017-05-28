package org.irreprimivel.montao.api.channel.service

import org.irreprimivel.montao.api.channel.entity.Channel

interface ChannelService {
    fun add(channel: Channel): Channel
    fun delete(channel: Channel)
    fun update(channel: Channel): Channel
    fun findAll(page: Int, limit: Int): List<Channel>
    fun findByCommunity(communityTitle: String, page: Int, limit: Int): List<Channel>
    fun findById(id: Long): Channel
    fun totalCount(): Long
    fun countByCommunity(communityTitle: String): Long
}
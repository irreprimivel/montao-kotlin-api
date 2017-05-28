package org.irreprimivel.montao.api.channel.service

import org.irreprimivel.montao.api.channel.dao.ChannelDAO
import org.irreprimivel.montao.api.channel.entity.Channel
import org.springframework.stereotype.Service

@Service
class ChannelServiceImpl(val channelDAO: ChannelDAO) : ChannelService {
    override fun add(channel: Channel): Channel = channelDAO.add(channel)

    override fun delete(channel: Channel) = channelDAO.delete(channel)

    override fun update(channel: Channel): Channel = channelDAO.update(channel)

    override fun findAll(page: Int, limit: Int): List<Channel> = channelDAO.findAll(page, limit)

    override fun findById(id: Long): Channel = channelDAO.findById(id)

    override fun findByCommunity(communityTitle: String, page: Int, limit: Int): List<Channel> = channelDAO
            .findByCommunity(communityTitle, page, limit)

    override fun totalCount(): Long = channelDAO.totalCount()

    override fun countByCommunity(communityTitle: String): Long = channelDAO.countByCommunity(communityTitle)
}
package org.irreprimivel.montao.api.channel.service

import org.springframework.stereotype.Service
import org.irreprimivel.montao.api.channel.dao.ChannelDAO
import org.irreprimivel.montao.api.channel.entity.Channel

@Service
class ChannelServiceImpl(val channelDAO: ChannelDAO) : ChannelService {
    override fun getAll(page: Int, limit: Int): List<Channel> = channelDAO.getAll(page, limit)

    override fun getByTitle(title: String): Channel = channelDAO.getByTitle(title)

    override fun add(channel: Channel) = channelDAO.add(channel)

    override fun delete(channel: Channel) = channelDAO.delete(channel)

    override fun update(channel: Channel): Channel = channelDAO.update(channel)

    override fun totalCount(): Long = channelDAO.totalCount()
}
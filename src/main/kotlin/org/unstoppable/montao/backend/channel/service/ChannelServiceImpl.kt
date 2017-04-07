package org.unstoppable.montao.backend.channel.service

import org.springframework.stereotype.Service
import org.unstoppable.montao.backend.channel.dao.ChannelDAO
import java.nio.channels.Channel

@Service
class ChannelServiceImpl(val channelDAO: ChannelDAO) : ChannelService {
    override fun getAll(page: Int, limit: Int): List<Channel> = channelDAO.getAll(page, limit)

    override fun getByTitle(title: String): Channel = channelDAO.getByTitle(title)

    override fun add(channel: Channel) = channelDAO.add(channel)

    override fun delete(channel: Channel) = channelDAO.delete(channel)

    override fun update(channel: Channel): Channel = channelDAO.update(channel)

    override fun totalCount(): Long = channelDAO.totalCount()
}
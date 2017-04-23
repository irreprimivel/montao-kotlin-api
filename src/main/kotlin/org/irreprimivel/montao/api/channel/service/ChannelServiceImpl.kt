package org.irreprimivel.montao.api.channel.service

import org.springframework.stereotype.Service
import org.irreprimivel.montao.api.channel.dao.ChannelDAO
import org.irreprimivel.montao.api.channel.entity.Channel

@Service
class ChannelServiceImpl(val channelDAO: ChannelDAO) : ChannelService {
    override fun findById(id: Long): Channel = channelDAO.findById(id)

    override fun findAll(page: Int, limit: Int): List<Channel> = channelDAO.findAll(page, limit)

    override fun findByTitle(title: String): Channel = channelDAO.findByTitle(title)

    override fun add(channel: Channel) = channelDAO.add(channel)

    override fun delete(channel: Channel) = channelDAO.delete(channel)

    override fun update(channel: Channel): Channel = channelDAO.update(channel)

    override fun totalCount(): Long = channelDAO.totalCount()
}
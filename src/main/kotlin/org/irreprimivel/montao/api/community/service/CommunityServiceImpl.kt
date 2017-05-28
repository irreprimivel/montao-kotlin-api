package org.irreprimivel.montao.api.community.service

import org.irreprimivel.montao.api.community.dao.CommunityDAO
import org.irreprimivel.montao.api.community.entity.Community
import org.springframework.stereotype.Service

@Service
class CommunityServiceImpl(val communityDAO: CommunityDAO) : CommunityService {
    override fun add(community: Community): Community = communityDAO.add(community)

    override fun delete(community: Community) = communityDAO.delete(community)

    override fun update(community: Community): Community = communityDAO.update(community)

    override fun findAll(page: Int, limit: Int): List<Community> = communityDAO.findAll(page, limit)

    override fun findByTitle(title: String): Community = communityDAO.findByTitle(title)

    override fun findById(id: Long): Community = communityDAO.findById(id)

    override fun totalCount(): Long = communityDAO.totalCount()
}
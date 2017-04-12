package org.irreprimivel.montao.api.community.service

import org.irreprimivel.montao.api.community.dao.CommunityDAO
import org.irreprimivel.montao.api.community.entity.Community
import org.springframework.stereotype.Service

@Service
class CommunityServiceImpl(val communityDAO: CommunityDAO) : CommunityService {
    override fun add(community: Community) = communityDAO.add(community)

    override fun delete(community: Community) = communityDAO.delete(community)

    override fun update(community: Community): Community = communityDAO.update(community)

    override fun getAll(page: Int, limit: Int): List<Community> = communityDAO.getAll(page, limit)

    override fun getByTitle(title: String): Community = communityDAO.getByTitle(title)

    override fun totalCount(): Long = communityDAO.totalCount()
}
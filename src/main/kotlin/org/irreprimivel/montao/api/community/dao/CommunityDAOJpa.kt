package org.irreprimivel.montao.api.community.dao

import org.irreprimivel.montao.api.community.entity.Community
import org.springframework.stereotype.Repository

@Repository
class CommunityDAOJpa : CommunityDAO {
    override fun add(community: Community) {
    }

    override fun delete(community: Community) {
    }

    override fun update(community: Community): Community {
    }

    override fun getAll(page: Int, limit: Int): List<Community> {
    }

    override fun getByTitle(title: String): Community {
    }

    override fun totalCount(): Long {
    }
}
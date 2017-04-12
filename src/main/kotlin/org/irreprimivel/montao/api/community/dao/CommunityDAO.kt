package org.irreprimivel.montao.api.community.dao

import org.irreprimivel.montao.api.community.entity.Community

interface CommunityDAO {
    fun add(community: Community)
    fun delete(community: Community)
    fun update(community: Community): Community
    fun getAll(page: Int, limit: Int): List<Community>
    fun getByTitle(title: String): Community
    fun totalCount(): Long
}
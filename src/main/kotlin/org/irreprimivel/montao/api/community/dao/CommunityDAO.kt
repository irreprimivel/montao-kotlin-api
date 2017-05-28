package org.irreprimivel.montao.api.community.dao

import org.irreprimivel.montao.api.community.entity.Community

interface CommunityDAO {
    fun add(community: Community): Community
    fun delete(community: Community)
    fun update(community: Community): Community
    fun findAll(page: Int, limit: Int): List<Community>
    fun findByTitle(title: String): Community
    fun findById(id: Long): Community
    fun totalCount(): Long
}
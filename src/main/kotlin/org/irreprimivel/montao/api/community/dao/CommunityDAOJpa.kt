package org.irreprimivel.montao.api.community.dao

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.community.exception.CommunityNotFoundException
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class CommunityDAOJpa(@PersistenceContext val entityManager: EntityManager) : CommunityDAO {
    override fun add(community: Community) = entityManager.persist(community)

    override fun delete(community: Community) = entityManager.remove(community)

    override fun update(community: Community): Community = entityManager.merge(community)

    override fun getAll(page: Int, limit: Int): List<Community> = entityManager
            .createQuery("select Community from Community", Community::class.java)
            .setMaxResults(limit)
            .setFirstResult((page - 1) * limit)
            .resultList

    override fun getByTitle(title: String): Community = entityManager
            .createQuery("select Community from Community where title = :title", Community::class.java)
            .setParameter("title", title)
            .resultList.stream()
            .findFirst()
            .orElseThrow {
                CommunityNotFoundException("Community with $title title not found")
            }

    override fun totalCount(): Long = entityManager.createQuery("select id from Community", Community::class.java)
            .maxResults
            .toLong()
}
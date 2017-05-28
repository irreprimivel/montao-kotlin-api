package org.irreprimivel.montao.api.community.dao

import org.irreprimivel.montao.api.community.entity.Community
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManagerFactory

@Repository
@Transactional
class CommunityDAOJpa(entityManagerFactory: EntityManagerFactory) : CommunityDAO {
    private val entityManager = entityManagerFactory.createEntityManager()

    override fun add(community: Community): Community {
        entityManager.run {
            persist(community)
            flush()
        }
        return community
    }

    override fun delete(community: Community) = entityManager.remove(community)

    override fun update(community: Community): Community = entityManager.merge(community)

    override fun findAll(page: Int, limit: Int): List<Community> = entityManager
            .createQuery("select c from Community c", Community::class.java)
            .setMaxResults(limit)
            .setFirstResult((page - 1) * limit)
            .resultList

    override fun findByTitle(title: String): Community = entityManager
            .createQuery("select c from Community c where c.title = :title", Community::class.java)
            .setParameter("title", title)
            .resultList.stream()
            .findFirst()
            .orElseThrow { NoSuchElementException("Community with $title title not found") }

    override fun findById(id: Long): Community = entityManager
            .createQuery("select c from Community c where c.id = :id", Community::class.java)
            .setParameter("id", id)
            .resultList.stream()
            .findFirst()
            .orElseThrow { NoSuchElementException("Coommunity with $id id not found") }

    override fun totalCount(): Long = entityManager
            .createQuery("select count(c.id) from Community c")
            .singleResult as Long
}
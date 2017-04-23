package org.irreprimivel.montao.api.community.dao

import org.irreprimivel.montao.api.community.entity.Community
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException
import javax.persistence.EntityManagerFactory

@Repository
@Transactional
class CommunityDAOJpa(val entityManagerFactory: EntityManagerFactory) : CommunityDAO {
    override fun add(community: Community) = entityManagerFactory.createEntityManager().persist(community)

    override fun delete(community: Community) = entityManagerFactory.createEntityManager().remove(community)

    override fun update(community: Community): Community = entityManagerFactory.createEntityManager().merge(community)

    override fun findAll(page: Int, limit: Int): List<Community> = entityManagerFactory.createEntityManager()
            .createQuery("select c from Community c", Community::class.java)
            .setMaxResults(limit)
            .setFirstResult((page - 1) * limit)
            .resultList

    override fun findByTitle(title: String): Community = entityManagerFactory.createEntityManager()
            .createQuery("select c from Community c where c.title = :title", Community::class.java)
            .setParameter("title", title)
            .resultList.stream()
            .findFirst()
            .orElseThrow { NoSuchElementException("Community with $title title not found") }

    override fun totalCount(): Long = entityManagerFactory.createEntityManager()
            .createQuery("select count(c.id) from Community c")
            .singleResult as Long
}
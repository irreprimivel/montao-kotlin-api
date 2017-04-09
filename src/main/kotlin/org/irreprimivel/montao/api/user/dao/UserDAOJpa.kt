package org.irreprimivel.montao.api.user.dao

import org.irreprimivel.montao.api.user.User
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class UserDAOJpa(@PersistenceContext val entityManager: EntityManager) : UserDAO {
    override fun getAll(page: Int, limit: Int): List<User> = entityManager
            .createQuery("select User from User", User::class.java)
            .setMaxResults(limit)
            .setFirstResult((page - 1) * limit)
            .resultList

    override fun getByUsername(username: String): User = entityManager
            .createQuery("select User from User where username = :username", User::class.java)
            .setParameter("username", username)
            .resultList
            .first()

    override fun add(user: User) = entityManager.persist(user)

    override fun delete(user: User) = entityManager.remove(user)

    override fun update(user: User): User = entityManager.merge(user)

    override fun totalCount(): Long = entityManager
            .createQuery("select count(id) from User", User::class.java)
            .singleResult as Long

    override fun getByEmail(email: String): User = entityManager
            .createQuery("select User from User where email = :email", User::class.java)
            .setParameter("email", email)
            .resultList
            .first()
}
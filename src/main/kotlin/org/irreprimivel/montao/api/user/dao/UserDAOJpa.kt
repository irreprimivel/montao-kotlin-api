package org.irreprimivel.montao.api.user.dao

import org.irreprimivel.montao.api.user.User
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException
import javax.persistence.EntityManagerFactory

@Repository
@Transactional
class UserDAOJpa(val entityManagerFactory: EntityManagerFactory) : UserDAO {
    override fun add(user: User) = entityManagerFactory.createEntityManager().persist(user)

    override fun delete(user: User) = entityManagerFactory.createEntityManager().remove(user)

    override fun update(user: User): User = entityManagerFactory.createEntityManager().merge(user)

    override fun findAll(page: Int, limit: Int): List<User> = entityManagerFactory.createEntityManager()
            .createQuery("select u from User u", User::class.java)
            .setMaxResults(limit)
            .setFirstResult((page - 1) * limit)
            .resultList

    override fun findByUsername(username: String): User = entityManagerFactory.createEntityManager()
            .createQuery("select u from User u where u.username = :username", User::class.java)
            .setParameter("username", username)
            .resultList.stream()
            .findFirst()
            .orElseThrow { NoSuchElementException("User with $username username not found") }

    override fun findByEmail(email: String): User = entityManagerFactory.createEntityManager()
            .createQuery("select u from User u where u.email = :email", User::class.java)
            .setParameter("email", email)
            .resultList.stream()
            .findFirst()
            .orElseThrow { NoSuchElementException("User with $email email not found") }

    override fun totalCount(): Long = entityManagerFactory.createEntityManager()
            .createQuery("select count(u.id) from User u")
            .singleResult as Long
}
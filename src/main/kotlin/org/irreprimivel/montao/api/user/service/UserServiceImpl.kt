package org.irreprimivel.montao.api.user.service

import org.irreprimivel.montao.api.user.User
import org.springframework.stereotype.Service
import org.irreprimivel.montao.api.user.dao.UserDAO
import java.math.BigInteger

@Service
class UserServiceImpl(val userDAO: UserDAO) : UserService {
    override fun add(user: User): User = userDAO.add(user)

    override fun findAll(page: Int, limit: Int): List<User> = userDAO.findAll(page, limit)

    override fun findByUsername(username: String): User = userDAO.findByUsername(username)

    override fun delete(user: User) = userDAO.delete(user)

    override fun update(user: User): User = userDAO.update(user)

    override fun totalCount(): Long = userDAO.totalCount()

    override fun findByEmail(email: String): User = userDAO.findByEmail(email)
}
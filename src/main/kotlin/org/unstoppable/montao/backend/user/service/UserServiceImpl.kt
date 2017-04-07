package org.unstoppable.montao.backend.user.service

import org.springframework.stereotype.Service
import org.unstoppable.montao.backend.subscription.SubscriptionDAO
import org.unstoppable.montao.backend.user.dao.UserDAO
import org.unstoppable.montao.backend.user.User

@Service
class UserServiceImpl(val userDAO: UserDAO, val subscriptionDAO: SubscriptionDAO) : UserService {
    override fun add(user: User) = userDAO.add(user)

    override fun getAll(page: Int, limit: Int): List<User> = userDAO.getAll(page, limit)

    override fun getByUsername(username: String): User = userDAO.getByUsername(username)

    override fun delete(user: User) = userDAO.delete(user)

    override fun update(user: User): User = userDAO.update(user)

    override fun totalCount(): Long = userDAO.totalCount()

    override fun getByEmail(email: String): User = userDAO.getByEmail(email)
}
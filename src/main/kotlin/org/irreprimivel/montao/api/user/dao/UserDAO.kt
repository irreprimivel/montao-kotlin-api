package org.irreprimivel.montao.api.user.dao

import org.irreprimivel.montao.api.user.User
import java.math.BigInteger

interface UserDAO {
    fun add(user: User): User
    fun delete(user: User)
    fun update(user: User): User
    fun findAll(page: Int, limit: Int): List<User>
    fun findByUsername(username: String): User
    fun findByEmail(email: String): User
    fun totalCount(): Long
}
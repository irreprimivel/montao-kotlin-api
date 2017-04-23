package org.irreprimivel.montao.api.user.service

import org.irreprimivel.montao.api.user.User
import java.math.BigInteger

interface UserService {
    fun add(user: User)
    fun findAll(page: Int, limit: Int): List<User>
    fun findByUsername(username: String): User
    fun delete(user: User)
    fun update(user: User): User
    fun totalCount(): Long
    fun findByEmail(email: String): User
}
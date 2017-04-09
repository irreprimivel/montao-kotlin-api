package org.irreprimivel.montao.api.user.dao

import org.irreprimivel.montao.api.user.User

interface UserDAO {
    fun getAll(page: Int, limit: Int): List<User>
    fun getByUsername(username: String): User
    fun add(user: User)
    fun delete(user: User)
    fun update(user: User): User
    fun totalCount(): Long
    fun getByEmail(email: String): User
}
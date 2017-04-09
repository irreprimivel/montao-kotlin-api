package org.irreprimivel.montao.api.user.service

import org.irreprimivel.montao.api.user.User

interface UserService {
    fun add(user: User)
    fun getAll(page: Int, limit: Int): List<User>
    fun getByUsername(username: String): User
    fun delete(user: User)
    fun update(user: User): User
    fun totalCount(): Long
    fun getByEmail(email: String): User
}
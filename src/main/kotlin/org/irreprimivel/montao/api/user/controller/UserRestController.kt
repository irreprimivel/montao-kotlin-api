package org.irreprimivel.montao.api.user.controller

import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.subscription.SubscriptionDAO
import org.irreprimivel.montao.api.user.User
import org.irreprimivel.montao.api.user.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Рестовый контроллер, который занимается выдачей данных по пользователям.
 * Роутинг:
 * - POST   /users/                         - создает нового пользователя
 * - PUT    /users/                         - исправляет уже имеющуюся запись
 * - DELETE /users/                         - удаляет уже имеющуюся запись
 * - GET    /users/                         - выдает всех пользователей
 * - GET    /users/{username}               - ищет пользователя с таким юзернэймом
 * - GET    /users/{username}/communities   - выдает все сообщества в которых состоит данный пользователь
 * - HEAD   /users/username?={username}     - проверяет существование такого имени пользователя
 * - HEAD   /users/email?={email}           - проверяет существование такой почты
 */
@RestController
@RequestMapping(value = "/users")
class UserRestController(val userService: UserService, val subscriptionDAO: SubscriptionDAO) {
    @PostMapping(produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun add(@RequestBody user: User, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<User> {
        userService.add(user)
        val location = uriComponentsBuilder.path("/users/{username}").buildAndExpand(user.username).toUri()
        return ResponseEntity.created(location).build()
    }

    @PutMapping(
            consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun update(@RequestBody user: User): ResponseEntity<User> {
        return ResponseEntity.ok(userService.update(user))
    }

    @DeleteMapping(
            consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun delete(@RequestBody user: User): ResponseEntity<User> {
        userService.delete(user)
        return ResponseEntity.ok(user)
    }

    @GetMapping(produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun getAll(page: Int = 1, limit: Int = 30): ResponseEntity<List<User>> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", userService.totalCount().toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        return ResponseEntity.ok().headers(headers).body(userService.getAll(page, limit))
    }

    @GetMapping(value = "/{username}", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun getByUsername(@PathVariable username: String): ResponseEntity<User> = ResponseEntity
            .ok(userService.getByUsername(username))

    @GetMapping(value = "/{username}/communities/", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun getUserCommunities(@PathVariable username: String,
                           page: Int,
                           limit: Int): ResponseEntity<List<Community>> {

        return ResponseEntity.ok(subscriptionDAO.getByUser(userService.getByUsername(username), page, limit))
    }

    /**
     * Проверяем существование такого юзернэйма.
     *
     * @param   username Юзернейм.
     * @return  Статус 200 - если есть, 404 - если нет.
     */
    @RequestMapping(method = arrayOf(RequestMethod.HEAD))
    fun checkUsername(@RequestParam username: String): ResponseEntity<*> = ResponseEntity
            .ok(userService.getByUsername(username))


    /**
     * Проверяет вуществование такой почты
     *
     * @param   email Почта.
     * @return  Статус 200 - если есть, 404 - если нет.
     */
    @RequestMapping(method = arrayOf(RequestMethod.HEAD))
    fun checkEmail(@RequestParam email: String): ResponseEntity<*> = ResponseEntity.ok(userService.getByEmail(email))
}

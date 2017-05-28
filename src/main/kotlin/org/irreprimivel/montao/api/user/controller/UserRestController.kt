package org.irreprimivel.montao.api.user.controller

import org.irreprimivel.montao.api.subscription.dao.SubscriptionDAO
import org.irreprimivel.montao.api.user.User
import org.irreprimivel.montao.api.user.service.UserService
import org.irreprimivel.montao.api.util.JsonUtil
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.HEAD
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
 * - HEAD   /users?username={username}      - проверяет существование такого имени пользователя
 * - HEAD   /users?email={email}            - проверяет существование такой почты
 */

@RestController
@RequestMapping(value = "/users")
class UserRestController(val userService: UserService, val subscriptionDAO: SubscriptionDAO) {
    @PostMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun add(@RequestBody user: User, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<User> {
        val createdUser = userService.add(user)
        val location = uriComponentsBuilder.path("/users/{username}").buildAndExpand(createdUser.username).toUri()
        return ResponseEntity.created(location).body(createdUser)
    }

    @PutMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun update(@RequestBody user: User): ResponseEntity<User> = ResponseEntity.ok(userService.update(user))

    @DeleteMapping(consumes = arrayOf(APPLICATION_JSON_UTF8_VALUE), produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun delete(@RequestBody user: User): ResponseEntity<User> {
        userService.delete(user)
        return ResponseEntity.ok(user)
    }

    /**
     * Возвращает список зарегистрированных пользователей.
     */
    @GetMapping(produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findAll(@RequestParam(name = "p", defaultValue = "1") page: Int,
                @RequestParam(name = "l", defaultValue = "30") limit: Int,
                @RequestParam(name = "f", required = false) fields: Array<out String>?): ResponseEntity<String> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", userService.totalCount().toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        val list = userService.findAll(page, limit)
        return ResponseEntity.ok().headers(headers).body(JsonUtil.objectToJsonString(fields, "user", list))
    }

    /**
     * Возвращает информацию о зарегистрированном пользователе с данным именем.
     * Примеры:
     * .../api/users/qwerty
     * .../api/io_78954?f=id,username,email
     *
     * @param username  Имя пользователя.
     * @param fields    Ожидаемые поля возвращаемого объекта. Является не обязательным полем. Если не указано,
     *                  то выводятся все поля объекта.
     * @return JSON объект пользователя.
     */
    @GetMapping(value = "/{username}", produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findByUsername(@PathVariable username: String,
                       @RequestParam(name = "f", required = false) fields: Array<out String>?): ResponseEntity<String> {
        val user = userService.findByUsername(username)
        return ResponseEntity.ok(JsonUtil.objectToJsonString(fields, "user", user))
    }

    /**
     * Возвращает список сообществ конкретного зарегистрированного пользователя.
     * Примеры:
     * .../api/users/nickname5000/communities
     * .../api/users/user1234/communities?p=2&l=70&f=id,title
     * .../api/users/hello555/communities?p=3&f=title
     * и т.д.
     *
     * @param username  Имя пользователя.
     * @param page      Страница списка. Если не указана, то по умолчанию в значение подставляется 1.
     * @param limit     Количество записей на странице. Если не указано, то в значение подставляется 30.
     * @param fields    Ожидаемые поля возвращаемых объектов. Является не обязательным полем. Если не указано,
     *                  то выводятся все поля объектов.
     * @return Список сообществ в виде JSON.
     */
    @GetMapping(value = "/{username}/communities/",
                produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    fun findCommunitiesByUser(@PathVariable username: String,
                              @RequestParam(name = "p", defaultValue = "1") page: Int,
                              @RequestParam(name = "l", defaultValue = "30") limit: Int,
                              @RequestParam(name = "f", required = false) fields: Array<out String>?): ResponseEntity<String> {
        val headers = HttpHeaders()
        with(headers) {
            set("X-Pagination-Count", userService.totalCount().toString())
            set("X-Pagination-Page", page.toString())
            set("X-Pagination-Limit", limit.toString())
        }
        val communities = subscriptionDAO.findByUser(userService.findByUsername(username), page, limit)
        return ResponseEntity.ok().headers(headers).body(JsonUtil.objectToJsonString(fields, "community", communities))
    }

    /**
     * Проверяет существование такого юзернэйма.
     *
     * @param username    Имя пользователя.
     * @return Статус 200 - если есть, 404 - если нет.
     */
    @RequestMapping(params = arrayOf("username"), method = arrayOf(HEAD))
    fun checkUsername(@RequestParam(name = "u") username: String): ResponseEntity<*> = ResponseEntity
            .ok(userService.findByUsername(username))

    /**
     * Проверяет существование такой почты.
     *
     * @param email   Почта.
     * @return Статус 200 - если есть, 404 - если нет.
     */
    @RequestMapping(params = arrayOf("email"), method = arrayOf(HEAD))
    fun checkEmail(@RequestParam(name = "e") email: String): ResponseEntity<*> = ResponseEntity
            .ok(userService.findByEmail(email))
}

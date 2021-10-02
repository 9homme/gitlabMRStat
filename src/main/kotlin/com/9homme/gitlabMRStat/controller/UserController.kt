package com.9homme.gitlabMRStat.controller

import com.9homme.gitlabMRStat.helper.UserCache
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userCache: UserCache) {

    @GetMapping("/users")
    fun getAllUsers() = userCache.getAllUsers()
}
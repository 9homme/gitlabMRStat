package com.9homme.gitlabMRStat.controller

import com.9homme.gitlabMRStat.helper.GitlabApiInvoker
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(private val gitlabApiInvoker: GitlabApiInvoker) {

    @GetMapping("/health")
    fun health() = "Ok"

    @GetMapping("/health/detail")
    fun detail(): String {
        return if (gitlabApiInvoker.getUserByUsername("saravuth.r") != null) "Ok" else "Failed"
    }
}
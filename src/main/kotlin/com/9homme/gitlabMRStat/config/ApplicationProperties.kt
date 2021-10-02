package com.9homme.gitlabMRStat.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ApplicationProperties {
    @Value("\${gitlab.url}")
    lateinit var gitlabUrl: String

    @Value("\${gitlab.token}")
    lateinit var gitlabToken: String

    @Value("\${gitlab.systemUsernames}")
    lateinit var gitlabSystemUsernames: List<String>
}
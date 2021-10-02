package com.9homme.gitlabMRStat.model

import java.time.ZonedDateTime

data class Event(
    val description: String,
    val author: String,
    val dateTime: String,
    val realDateTime: ZonedDateTime
)

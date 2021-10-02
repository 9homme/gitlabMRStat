package com.9homme.gitlabMRStat.model

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.ZonedDateTime

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Note(
    val id: Long,
    val body: String,
    val author: User,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val system: Boolean
)

/*
 {
        "id": 694894,
        "type": null,
        "body": "resolved all threads",
        "attachment": null,
        "author": {
            "id": 181,
            "name": "dacharat",
            "username": "dacharat",
            "state": "active",
            "avatar_url": "https://gitlab.com/uploads/-/system/user/avatar/181/avatar.png",
            "web_url": "https://gitlab.com/dacharat"
        },
        "created_at": "2021-01-24T13:52:34.060+07:00",
        "updated_at": "2021-01-24T13:52:34.063+07:00",
        "system": true,
        "noteable_id": 61300,
        "noteable_type": "MergeRequest",
        "resolvable": false,
        "confidential": false,
        "noteable_iid": 143,
        "commands_changes": {}
    }
 */

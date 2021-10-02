package com.9homme.gitlabMRStat.model

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.ZonedDateTime

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class LabelEvent(
    val id: Long,
    val user: User,
    val createdAt: ZonedDateTime,
    val resourceType: String,
    val resourceId: Long,
    val label: Label?,
    val action: String
)

/*
{
        "id": 143167,
        "user": {
            "id": 183,
            "name": "maptir",
            "username": "maptir",
            "state": "active",
            "avatar_url": "https://secure.gravatar.com/avatar/8c4487dca0f0fe48c6e43a6b758707f0?s=80&d=identicon",
            "web_url": "https://gitlab.com/maptir"
        },
        "created_at": "2021-01-26T15:49:25.781+07:00",
        "resource_type": "MergeRequest",
        "resource_id": 61648,
        "label": {
            "id": 4254,
            "name": "Ready for review",
            "color": "#69D100",
            "description": null,
            "description_html": "",
            "text_color": "#FFFFFF"
        },
        "action": "add"
    }
 */

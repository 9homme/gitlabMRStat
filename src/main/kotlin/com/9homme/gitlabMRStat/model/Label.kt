package com.9homme.gitlabMRStat.model

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Label(
    val id: Long,
    val name: String,
    val color: String,
    val description: String?,
    val descriptionHtml: String?,
    val textColor: String
)

/*
"label": {
            "id": 4254,
            "name": "Ready for review",
            "color": "#69D100",
            "description": null,
            "description_html": "",
            "text_color": "#FFFFFF"
        },
 */

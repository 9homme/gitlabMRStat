package com.9homme.gitlabMRStat.model

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.ZonedDateTime

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Commit(
    val id: String,
    val shortId: String,
    val createdAt: ZonedDateTime,
    val title: String,
    val message: String,
    val authorName: String,
    val authorEmail: String,
    val authoredDate: ZonedDateTime,
    val committerName: String,
    val committerEmail: String,
    val committedDate: ZonedDateTime,
    val stats: CommitStat?
)

/*
{
        "id": "b48cc4d376d639f338aa0f345decf8a5fe266ad6",
        "short_id": "b48cc4d3",
        "created_at": "2021-01-22T14:54:21.000+07:00",
        "parent_ids": [],
        "title": "Update README.md",
        "message": "Update README.md",
        "author_name": "Saravuth Ruangsutham",
        "author_email": "saravuth.r@9homme.com",
        "authored_date": "2021-01-22T14:54:21.000+07:00",
        "committer_name": "Saravuth Ruangsutham",
        "committer_email": "saravuth.r@9homme.com",
        "committed_date": "2021-01-22T14:54:21.000+07:00",
        "web_url": "https://gitlab.com/9homme/food-service/-/commit/b48cc4d376d639f338aa0f345decf8a5fe266ad6"
    }
 */

/*
{
    "id": "b48cc4d376d639f338aa0f345decf8a5fe266ad6",
    "short_id": "b48cc4d3",
    "created_at": "2021-01-22T07:54:21.000+00:00",
    "parent_ids": [
        "1acd0ce8299c8379ac766157a80b84a75ef5c67e"
    ],
    "title": "Update README.md",
    "message": "Update README.md",
    "author_name": "Saravuth Ruangsutham",
    "author_email": "saravuth.r@9homme.com",
    "authored_date": "2021-01-22T07:54:21.000+00:00",
    "committer_name": "Saravuth Ruangsutham",
    "committer_email": "saravuth.r@9homme.com",
    "committed_date": "2021-01-22T07:54:21.000+00:00",
    "web_url": "https://gitlab.com/9homme/food-service/-/commit/b48cc4d376d639f338aa0f345decf8a5fe266ad6",
    "stats": {
        "additions": 1,
        "deletions": 0,
        "total": 1
    },
    "status": "success",
    "project_id": 777,
    "last_pipeline": {
        "id": 292021,
        "sha": "b48cc4d376d639f338aa0f345decf8a5fe266ad6",
        "ref": "orm/test-mr",
        "status": "success",
        "created_at": "2021-01-22T14:55:44.144+07:00",
        "updated_at": "2021-01-22T14:57:53.564+07:00",
        "web_url": "https://gitlab.com/9homme/food-service/-/pipelines/292021"
    }
}
 */
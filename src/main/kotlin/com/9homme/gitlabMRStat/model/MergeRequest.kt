package com.9homme.gitlabMRStat.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.ZonedDateTime

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class MergeRequest(
    val id: Long,
    @JsonProperty("iid")
    val mergeRequestNo: Int,
    val projectId: Int,
    val title: String,
    val description: String,
    val state: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val mergedAt: ZonedDateTime?,
    val closedAt: ZonedDateTime?,
    val targetBranch: String,
    val sourceBranch: String,
    val userNotesCount: Int,
    val author: User,
    val reviewers: List<User>?,
    val references: MergeRequestReference,
    val changesCount: String?
)
/*
{
        "id": 61187,
        "iid": 141,
        "project_id": 777,
        "title": "[Test - Do not review]Update README.md",
        "description": "",
        "state": "opened",
        "created_at": "2021-01-22T14:55:07.316+07:00",
        "updated_at": "2021-01-22T15:00:33.851+07:00",
        "merged_by": null,
        "merged_at": null,
        "closed_by": null,
        "closed_at": null,
        "target_branch": "develop",
        "source_branch": "orm/test-mr",
        "user_notes_count": 2,
        "upvotes": 0,
        "downvotes": 0,
        "author": {
            "id": 373,
            "name": "Saravuth Ruangsutham",
            "username": "saravuth.r",
            "state": "active",
            "avatar_url": "https://secure.gravatar.com/avatar/8aa5ccc3b59afda4f046f92a2e375811?s=80&d=identicon",
            "web_url": "https://gitlab.com/saravuth.r"
        },
        "assignees": [],
        "assignee": null,
        "source_project_id": 777,
        "target_project_id": 777,
        "labels": [],
        "work_in_progress": false,
        "milestone": null,
        "merge_when_pipeline_succeeds": false,
        "merge_status": "unchecked",
        "sha": "b48cc4d376d639f338aa0f345decf8a5fe266ad6",
        "merge_commit_sha": null,
        "squash_commit_sha": null,
        "discussion_locked": null,
        "should_remove_source_branch": null,
        "force_remove_source_branch": false,
        "reference": "!141",
        "references": {
            "short": "!141",
            "relative": "!141",
            "full": "9homme/food-service!141"
        },
        "web_url": "https://gitlab.com/9homme/food-service/-/merge_requests/141",
        "time_stats": {
            "time_estimate": 0,
            "total_time_spent": 0,
            "human_time_estimate": null,
            "human_total_time_spent": null
        },
        "squash": false,
        "task_completion_status": {
            "count": 0,
            "completed_count": 0
        },
        "has_conflicts": false,
        "blocking_discussions_resolved": false
    }
 */

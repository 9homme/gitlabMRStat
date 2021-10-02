package com.9homme.gitlabMRStat.model

data class MergeRequestStat(
    val user: User,
    val mergeRequest: MergeRequest,
    val commits: List<Commit>,
    val notes: List<Note>,
    val events: List<Event>,
    val totalNoOfNotes: Int,
    val pingPongTimes: Int,
    val totalNoOfChanges: String,
    val createdAt: String,
    val readyForReviewAt: String,
    val mergedAt: String,
    val readyToMergedPeriod: String,
    val openToMergedPeriod: String
)

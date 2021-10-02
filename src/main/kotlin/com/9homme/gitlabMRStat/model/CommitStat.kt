package com.9homme.gitlabMRStat.model

data class CommitStat(
    val additions: Int,
    val deletions: Int,
    val total: Int
)

/*
"stats": {
        "additions": 1,
        "deletions": 0,
        "total": 1
    }
 */

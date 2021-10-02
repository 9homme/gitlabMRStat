package com.9homme.gitlabMRStat.controller

import com.9homme.gitlabMRStat.helper.GitlabApiInvoker
import com.9homme.gitlabMRStat.helper.GitlabStatHelper
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MergeRequestController(private val gitlabApiInvoker: GitlabApiInvoker,
                             private val gitlabStatHelper: GitlabStatHelper
) {

    @GetMapping("/merge_requests")
    fun getMergeRequest(@RequestParam username: String) = runBlocking {
        ResponseEntity.ok(gitlabApiInvoker.getMergeRequests(username))
    }

    @GetMapping("/merge_requests/commits")
    fun getMergeRequestCommits() = ResponseEntity.ok(gitlabApiInvoker.getMergeRequestCommits(777, 143))

    //54f5020286fdaa2f335c909c8de8b19ac2524083
    @GetMapping("/merge_requests/commits/detail")
    fun getCommitDetail() = ResponseEntity.ok(gitlabApiInvoker.getCommit(777, "54f5020286fdaa2f335c909c8de8b19ac2524083") ?: "Not found!")

    @GetMapping("/merge_requests/notes")
    fun getNotes() = ResponseEntity.ok(gitlabApiInvoker.getMergeRequestNotes(777, 143))

    @GetMapping("/merge_requests/stats")
    fun getMergeRequestStat(@RequestParam username: String) =
        ResponseEntity.ok(gitlabStatHelper.getStats(username))

}
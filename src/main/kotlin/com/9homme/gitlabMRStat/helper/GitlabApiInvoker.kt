package com.9homme.gitlabMRStat.helper

import com.9homme.gitlabMRStat.config.ApplicationProperties
import com.9homme.gitlabMRStat.helper.Extensions.parallelMap
import com.9homme.gitlabMRStat.model.*
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class GitlabApiInvoker(
    private val applicationProperties: ApplicationProperties,
    private val restTemplate: RestTemplate
) {
    companion object {
        private val logger = LoggerFactory.getLogger(GitlabApiInvoker::class.java)
    }

    //https://gitlab.com/api/v4/merge_requests?scope=all&author_username=saravuth.r
    suspend fun getMergeRequests(username: String): List<MergeRequest> {
        val url = "${applicationProperties.gitlabUrl}/merge_requests?scope=all&per_page=100&order_by=created_at&sort=desc&author_username=${username}"
        val httpEntity = HttpEntity(null, gitlabHeaders())
        val response = try {
            restTemplate.exchange(
                url,
                HttpMethod.GET, httpEntity, Array<MergeRequest>::class.java
            )
        } catch (e: Exception) {
            logger.error("call gitlab api error with message: ${e.message}.", e)
            null
        }
        val totalPages: Int = response?.headers?.get("X-Total-Pages")?.firstOrNull()?.toInt() ?: 1
        val firstPage = response?.body?.toList() ?: emptyList()
        val nextPages = if(totalPages > 1) {
            (2..totalPages).parallelMap {
                try {
                    restTemplate.exchange(
                        "$url&page=$it",
                        HttpMethod.GET, httpEntity, Array<MergeRequest>::class.java
                    ).body?.toList()
                } catch (e: Exception) {
                    logger.error("call gitlab api error with message: ${e.message}.", e)
                    null
                }
            }.filterNotNull().flatten()
        } else emptyList()
        return  firstPage + nextPages
    }

    //https://gitlab.com/api/v4/projects/777/repository/commits/b48cc4d376d639f338aa0f345decf8a5fe266ad6
    fun getCommit(projectId: Int, commitId: String): Commit? {
        val url = "${applicationProperties.gitlabUrl}/projects/$projectId/repository/commits/${commitId}"
        return callGitlabApi<Commit>(url)
    }

    //https://gitlab.com/api/v4/projects/777/merge_requests/141/commits
    fun getMergeRequestCommits(projectId: Int, mergeRequestNo: Int): List<Commit> {
        val url = "${applicationProperties.gitlabUrl}/projects/$projectId/merge_requests/$mergeRequestNo/commits?per_page=100"
        val response = callGitlabApi<Array<Commit>>(url)
        return response?.toList() ?: emptyList()
    }

    //https://gitlab.com/api/v4/projects/777/merge_requests/141
    fun getMergeRequest(projectId: Int, mergeRequestNo: Int): MergeRequest? {
        val url = "${applicationProperties.gitlabUrl}/projects/$projectId/merge_requests/$mergeRequestNo"
        return callGitlabApi<MergeRequest>(url)
    }

    //https://gitlab.com/api/v4/projects/777/merge_requests/141/resource_label_events
    fun getMergeRequestLabelEvents(projectId: Int, mergeRequestNo: Int): List<LabelEvent> {
        val url = "${applicationProperties.gitlabUrl}/projects/$projectId/merge_requests/$mergeRequestNo/resource_label_events?per_page=100"
        val response = callGitlabApi<Array<LabelEvent>>(url)
        return response?.toList() ?: emptyList()
    }

    //https://gitlab.com/api/v4/projects/777/merge_requests/143/notes
    fun getMergeRequestNotes(projectId: Int, mergeRequestNo: Int): List<Note> {
        val url = "${applicationProperties.gitlabUrl}/projects/$projectId/merge_requests/$mergeRequestNo/notes"
        val response = callGitlabApi<Array<Note>>(url)
        return response?.toList() ?: emptyList()
    }

    //https://gitlab.com/api/v4/users.json?username=dacharat
    fun getUserByUsername(username: String): User? {
        val url = "${applicationProperties.gitlabUrl}/users?username=$username"
        val response = callGitlabApi<Array<User>>(url)
        return response?.minByOrNull(User::id)
    }

    suspend fun getAllUsers(): List<User> {
        val url = "${applicationProperties.gitlabUrl}/users?active=true&blocked=false&external=false&per_page=100&with_custom_attributes=false"
        val httpEntity = HttpEntity(null, gitlabHeaders())
        val response = try {
            restTemplate.exchange(
                url,
                HttpMethod.GET, httpEntity, Array<User>::class.java
            )
        } catch (e: Exception) {
            logger.error("call gitlab api error with message: ${e.message}.", e)
            null
        }
        val totalPages: Int = response?.headers?.get("X-Total-Pages")?.firstOrNull()?.toInt() ?: 1
        val firstPage = response?.body?.toList() ?: emptyList()
        val nextPages = if(totalPages > 1) {
            (2..totalPages).parallelMap {
                try {
                    restTemplate.exchange(
                        "$url&page=$it",
                        HttpMethod.GET, httpEntity, Array<User>::class.java
                    ).body?.toList()
                } catch (e: Exception) {
                    logger.error("call gitlab api error with message: ${e.message}.", e)
                    null
                }
            }.filterNotNull().flatten()
        } else emptyList()
        return  firstPage + nextPages
    }

    private inline fun <reified T> callGitlabApi(
        url: String, httpMethod: HttpMethod = HttpMethod.GET, body: Any? = null
    ): T? {
        val httpEntity = HttpEntity(body, gitlabHeaders())
        val response = try {
            restTemplate.exchange(
                url,
                httpMethod, httpEntity, T::class.java
            )
        } catch (e: Exception) {
            logger.error("call gitlab api error with message: ${e.message}.", e)
            null
        }
        return response?.body
    }

    private fun gitlabHeaders(): HttpHeaders {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.set("Authorization", "Bearer ${applicationProperties.gitlabToken}")
        return httpHeaders
    }
}
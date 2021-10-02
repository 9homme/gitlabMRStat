package com.9homme.gitlabMRStat.helper

import com.9homme.gitlabMRStat.config.ApplicationProperties
import com.9homme.gitlabMRStat.helper.Extensions.parallelMap
import com.9homme.gitlabMRStat.helper.Extensions.toFormattedString
import com.9homme.gitlabMRStat.model.*
import kotlinx.coroutines.runBlocking
import org.joda.time.Period
import org.joda.time.format.PeriodFormatterBuilder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class GitlabStatHelper(
    private val gitlabApiInvoker: GitlabApiInvoker,
    private val applicationProperties: ApplicationProperties
) {
    companion object {
        private val logger = LoggerFactory.getLogger(GitlabStatHelper::class.java)
        private val periodFormatter = PeriodFormatterBuilder()
            .appendDays()
            .appendSuffix("d")
            .appendHours()
            .appendSuffix("h")
            .appendMinutes()
            .appendSuffix("m")
            .toFormatter()
    }

    suspend fun getCommits(projectId: Int, mergeRequestNo: Int) =
        gitlabApiInvoker.getMergeRequestCommits(projectId, mergeRequestNo)

    suspend fun getNotes(projectId: Int, mergeRequestNo: Int) =
        gitlabApiInvoker.getMergeRequestNotes(projectId, mergeRequestNo)
            .filter { note ->
                !applicationProperties.gitlabSystemUsernames.contains(note.author.username) && !note.system
            }

    suspend fun getReadyToReviewDateTime(projectId: Int, mergeRequestNo: Int) =
        gitlabApiInvoker.getMergeRequestLabelEvents(projectId, mergeRequestNo)
        .filter { label ->
            label.label?.name?.matches(Regex("ready.*review", RegexOption.IGNORE_CASE)) ?: false
                    && label.action == "add"
        }
        .minByOrNull(LabelEvent::createdAt)?.createdAt

    suspend fun getEvents(commits: List<Commit>, notes: List<Note>) = (
            commits.map { c -> Event("Commit", c.committerName, c.committedDate.toFormattedString(), c.committedDate) }
                    + notes.map { n -> Event(n.body, n.author.username, n.createdAt.toFormattedString() , n.createdAt) }
            ).sortedBy(Event::realDateTime)

    suspend fun calculatePingPongTimes(events: List<Event>, username: String) =
        events.filter { event -> event.description == "Commit" || !username.equals(event.author, true) }
            .let { it.withIndex().map { (index, value) ->
                if (index != 0 && value.description == "Commit" && it[index - 1].description != "Commit") 1 else 0 }
            }.sum()

    suspend fun calculateTotalNoOfChanges(commits: List<Commit>) = commits.mapNotNull { it.stats?.total }.sum()

    suspend fun getReadyToMergedPeriod(readyToReviewDateTime: ZonedDateTime?, mergedDateTime: ZonedDateTime?) =
        readyToReviewDateTime?.let { readyToReview ->
            mergedDateTime?.let { merged ->
                periodFormatter.print(
                    Period(
                        merged.toInstant().toEpochMilli() - readyToReview.toInstant().toEpochMilli()
                    ).normalizedStandard()
                )
            }
        } ?: "-"

    suspend fun getOpenToMergedPeriod(createdDateTime: ZonedDateTime, mergedDateTime: ZonedDateTime?) =
        mergedDateTime?.let { merged ->
            periodFormatter
                .print(Period(merged.toInstant().toEpochMilli() - createdDateTime.toInstant().toEpochMilli()).normalizedStandard())
        } ?: "-"

    suspend fun getMergeRequests(username: String) =
        gitlabApiInvoker.getMergeRequests(username)
            .parallelMap { gitlabApiInvoker.getMergeRequest(it.projectId, it.mergeRequestNo) }
            .filterNotNull()

    fun getStats(username: String): List<MergeRequestStat> {
        return runBlocking {
            val mergeRequests = getMergeRequests(username)
            mergeRequests.parallelMap { mr ->
                logger.debug("Start getting stat for MR#${mr.mergeRequestNo} $username at ${ZonedDateTime.now()}")
                val commits = getCommits(mr.projectId, mr.mergeRequestNo)
                val notes = getNotes(mr.projectId, mr.mergeRequestNo)
                val readyToReviewDateTime = getReadyToReviewDateTime(mr.projectId, mr.mergeRequestNo)
                val events = getEvents(commits, notes)
                val pingPongTimes = calculatePingPongTimes(events, mr.author.username)
                val totalNoOfChanges = mr.changesCount?:"-"//calculateTotalNoOfChanges(commits)
                logger.debug("Finished getting stat for MR#${mr.mergeRequestNo} $username at ${ZonedDateTime.now()}")

                MergeRequestStat(mr.author, mr, commits, notes, events, notes.size, pingPongTimes,
                    totalNoOfChanges, mr.createdAt.toFormattedString(),
                    readyToReviewDateTime?.toFormattedString() ?: "-",
                    mr.mergedAt?.toFormattedString() ?: "-",
                    getReadyToMergedPeriod(readyToReviewDateTime, mr.mergedAt),
                    getOpenToMergedPeriod(mr.createdAt, mr.mergedAt)
                )
            }.sortedByDescending { stat -> stat.mergeRequest.createdAt }
        }
    }

}
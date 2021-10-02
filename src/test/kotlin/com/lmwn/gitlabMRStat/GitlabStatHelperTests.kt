package com.9homme.gitlabMRStat

import com.9homme.gitlabMRStat.helper.GitlabStatHelper
import com.9homme.gitlabMRStat.model.Event
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.ZonedDateTime

@SpringBootTest
class GitlabStatHelperTests(
    @Autowired
    private val gitlabStatHelper: GitlabStatHelper
) {

    private val username = "test_username"

    @Test
    fun testPingPongTimeShouldReturnOne() {
        val events = listOf<Event>(
            Event("Commit", username, "", ZonedDateTime.now()),
            Event("Commit", username, "", ZonedDateTime.now()),
            Event("Please change", "reviewer1", "", ZonedDateTime.now()),
            Event("Please change", "reviewer2", "", ZonedDateTime.now()),
            Event("Commit", username, "", ZonedDateTime.now()),
            Event("Done", username, "", ZonedDateTime.now()),
            Event("Commit", username, "", ZonedDateTime.now())
        )
        val pingPongTimes = runBlocking {
            gitlabStatHelper.calculatePingPongTimes(events, username)
        }

        assert(pingPongTimes == 1)
    }

    @Test
    fun testPingPongTimeShouldReturnTwo() {
        val events = listOf<Event>(
            Event("Commit", username, "", ZonedDateTime.now()),
            Event("Commit", username, "", ZonedDateTime.now()),
            Event("Please change", "reviewer1", "", ZonedDateTime.now()),
            Event("Please change", "reviewer2", "", ZonedDateTime.now()),
            Event("Commit", username, "", ZonedDateTime.now()),
            Event("Done", username, "", ZonedDateTime.now()),
            Event("Commit", username, "", ZonedDateTime.now()),
            Event("Please change", "reviewer2", "", ZonedDateTime.now()),
            Event("Commit", username, "", ZonedDateTime.now())
        )
        val pingPongTimes = runBlocking {
            gitlabStatHelper.calculatePingPongTimes(events, username)
        }

        assert(pingPongTimes == 2)
    }


}
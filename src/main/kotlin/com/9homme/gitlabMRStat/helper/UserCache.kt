package com.9homme.gitlabMRStat.helper

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import com.9homme.gitlabMRStat.model.User
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class UserCache(private val gitlabApiInvoker: GitlabApiInvoker) {

    val key = "userList"
    val cache: LoadingCache<String?, List<User>?> =
        CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(
        CacheLoader.from {
            _ : String? -> runBlocking {gitlabApiInvoker.getAllUsers()}
        }
    )

    fun getAllUsers(): List<User> = cache.get(key)?: emptyList()
}
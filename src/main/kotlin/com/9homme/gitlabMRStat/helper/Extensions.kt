package com.9homme.gitlabMRStat.helper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object Extensions {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> = withContext(Dispatchers.IO) {
        map { async { f(it) } }.awaitAll()
    }

    fun ZonedDateTime.toFormattedString(): String = dateFormatter.format(this.withZoneSameInstant(ZoneId.systemDefault()))
}
package com.example.prioritask.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

object DateUtils {
    fun convertLocalDateTimeToDateInDefaultTimeZone(localDateTime: LocalDateTime): Date {
        val defaultTimeZone = ZoneId.systemDefault()
        val zonedDateTime = localDateTime.atZone(defaultTimeZone)
        return Date.from(zonedDateTime.toInstant())
    }
}
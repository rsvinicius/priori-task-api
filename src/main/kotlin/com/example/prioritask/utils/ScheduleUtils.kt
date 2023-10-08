package com.example.prioritask.utils

object ScheduleUtils {
    fun getTaskTriggerKey(taskId: Long) = "reminderTrigger_$taskId"

    fun getTaskJobKey(taskId: Long) = "reminderJob_$taskId"
}
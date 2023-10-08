package com.example.prioritask.service

import com.example.prioritask.scheduler.ReminderJob
import com.example.prioritask.utils.DateUtils
import com.example.prioritask.utils.ScheduleUtils.getTaskJobKey
import com.example.prioritask.utils.ScheduleUtils.getTaskTriggerKey
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.JobKey.jobKey
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey.triggerKey
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

private const val ONE_DAY = 1L

@Service
class ScheduleService(private val schedulerFactoryBean: SchedulerFactoryBean) {
    fun scheduleTaskReminder(userId: Long, taskId: Long, dueDate: LocalDate) {
        val triggerDateTime = calculateTriggerDateTime(dueDate)

        if (isTriggerDateTimeAfterCurrentDateTime(triggerDateTime)) {
            val scheduler = schedulerFactoryBean.scheduler
            val reminderJob = createReminderJob(taskId, userId)
            val trigger = createReminderTrigger(taskId, triggerDateTime)

            scheduler.scheduleJob(reminderJob, trigger)
        }
    }

    fun rescheduleTrigger(taskId: Long, dueDate: LocalDate) {
        val triggerDateTime = calculateTriggerDateTime(dueDate)

        if (isTriggerDateTimeAfterCurrentDateTime(triggerDateTime)) {
            val scheduler = schedulerFactoryBean.scheduler
            val trigger = createReminderTrigger(taskId, triggerDateTime)

            scheduler.rescheduleJob(triggerKey(getTaskTriggerKey(taskId)), trigger)
        } else {
            deleteJobByTaskId(taskId)
        }
    }

    fun deleteJobByTaskId(taskId: Long) {
        val scheduler = schedulerFactoryBean.scheduler

        scheduler.deleteJob(jobKey(getTaskJobKey(taskId)))
    }

    private fun createReminderJob(taskId: Long, userId: Long): JobDetail =
        JobBuilder.newJob(ReminderJob::class.java)
            .withIdentity(getTaskJobKey(taskId))
            .usingJobData("userId", userId)
            .usingJobData("taskId", taskId)
            .build()

    private fun createReminderTrigger(taskId: Long, triggerDateTime: LocalDateTime): Trigger =
        TriggerBuilder.newTrigger()
            .withIdentity(getTaskTriggerKey(taskId))
            .startAt(DateUtils.convertLocalDateTimeToDateInDefaultTimeZone(triggerDateTime))
            .build()


    private fun isTriggerDateTimeAfterCurrentDateTime(triggerDateTime: LocalDateTime) =
        triggerDateTime.isAfter(LocalDateTime.now())

    private fun calculateTriggerDateTime(dueDate: LocalDate): LocalDateTime =
        dueDate.minusDays(ONE_DAY).atTime(LocalTime.now())
}
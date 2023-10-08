package com.example.prioritask.scheduler

import com.example.prioritask.service.EmailService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class ReminderJob(
    private val emailService: EmailService
) : Job {

    override fun execute(context: JobExecutionContext) {
        val jobDataMap = context.jobDetail.jobDataMap

        val userId = jobDataMap["userId"] as Long
        val taskId = jobDataMap["taskId"] as Long

        emailService.sendReminderEmail(userId, taskId)
    }
}
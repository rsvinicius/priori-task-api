package com.example.prioritask.service

import com.example.prioritask.config.EmailConfig
import com.example.prioritask.model.constant.EmailConstants.REMINDER_EMAIL_SUBJECT
import com.example.prioritask.model.constant.EmailConstants.REMINDER_EMAIL_TEMPLATE
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.EmailException
import org.apache.commons.mail.HtmlEmail
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Service
class EmailService(
    private val templateEngine: SpringTemplateEngine,
    private val emailConfig: EmailConfig,
    private val userService: UserService,
    private val taskService: TaskService
) {
    fun sendReminderEmail(userId: Long, taskId: Long) {
        val user = userService.getUserById(userId)

        val task = taskService.findTaskById(taskId, user.id)

        val context = Context().also {
            it.setVariable("fullName", user.fullName)
            it.setVariable("taskTitle", task.title)
            it.setVariable("dueDate", task.dueDate)
        }

        val processedTemplate = templateEngine.process(REMINDER_EMAIL_TEMPLATE, context)

        sendEmail(user.email, REMINDER_EMAIL_SUBJECT, processedTemplate)
    }

    private fun sendEmail(recipientEmail: String, emailSubject: String, template: String) {
        val email = HtmlEmail()

        email.setHostName(emailConfig.hostName)
        email.setSmtpPort(emailConfig.smtpPort)
        email.setAuthenticator(DefaultAuthenticator(emailConfig.auth.username, emailConfig.auth.password))
        email.setSSLOnConnect(true)
        email.addTo(recipientEmail)
        email.setFrom(emailConfig.auth.username, emailConfig.senderName)
        email.setSubject(emailSubject)
        email.setHtmlMsg(template)

        try {
            email.send()
        } catch (e: EmailException) {
            throw e
        }
    }
}
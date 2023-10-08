package com.example.prioritask.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "email")
class EmailConfig {
    lateinit var senderName: String
    lateinit var hostName: String
    var smtpPort: Int = 0
    lateinit var auth: Authenticator

    class Authenticator {
        lateinit var username: String
        lateinit var password: String
    }
}
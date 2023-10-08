package com.example.prioritask.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode

@Configuration
class ThymeleafConfig {
    @Bean
    fun templateResolver(): SpringResourceTemplateResolver {
        return SpringResourceTemplateResolver().apply {
            prefix = "classpath:/templates/"
            suffix = ".html"
            templateMode = TemplateMode.HTML
            characterEncoding = "UTF-8"
        }
    }

    @Bean
    fun templateEngine(): SpringTemplateEngine {
        return SpringTemplateEngine().apply {
            setTemplateResolver(templateResolver())
            enableSpringELCompiler = true
        }
    }
}
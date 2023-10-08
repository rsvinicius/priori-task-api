package com.example.prioritask

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class PrioriTaskApplication

fun main(args: Array<String>) {
	runApplication<PrioriTaskApplication>(*args)
}

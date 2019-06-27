package com.amors.notifybot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Profile

@Profile("develop")
@SpringBootApplication
class BotApplication

fun main(args: Array<String>) {
    SpringApplication.run(BotApplication::class.java, *args)
}
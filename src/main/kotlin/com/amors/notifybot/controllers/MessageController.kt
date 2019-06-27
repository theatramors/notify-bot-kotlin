package com.amors.notifybot.controllers

import com.amors.notifybot.dto.Activity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController {

    val logger: Logger = LoggerFactory.getLogger(MessageController::class.java)

    /**
     * Эндпоинт приема сообщений
     */
    @PostMapping("/api/messages")
    fun processRequest(@RequestBody activity: Activity) {}
}
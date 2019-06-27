package com.amors.notifybot.controllers

import com.amors.notifybot.dto.Activity
import com.amors.notifybot.services.ActivityService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(private val activityService: ActivityService) {

    private val logger: Logger = LoggerFactory.getLogger(MessageController::class.java)

    /**
     * Endpoint for receiving messages
     */
    @PostMapping("/api/messages")
    fun processActivity(@RequestBody activity: Activity) {
        logger.info("Receiving message")
        activityService.processActivity(activity)
    }
}
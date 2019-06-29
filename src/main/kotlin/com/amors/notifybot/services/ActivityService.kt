package com.amors.notifybot.services

import com.amors.notifybot.dto.Activity
import com.amors.notifybot.dto.Attachment
import com.amors.notifybot.dto.CardImage
import com.amors.notifybot.dto.ChannelAccount
import com.amors.notifybot.dto.HeroCard
import com.amors.notifybot.enums.ActivityTypes
import com.amors.notifybot.enums.AttachmentContentTypes
import com.amors.notifybot.enums.Commands
import com.amors.notifybot.http.HttpClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service

@Service
class ActivityService(
    private val objectMapper: ObjectMapper,
    private val authenticationService: AuthenticationService
) {

    /**
     * Processing Activity
     *
     * @param activity activity object
     */
    fun processActivity(activity: Activity) {
        val conversation = activity.conversation!!

        val url = "${activity.serviceUrl}v3/conversations/${conversation.id}/activities"

        val commandIndex = if (conversation.isGroup!!) 1 else 0

        if (activity.action == "add") {
            sendWelcomeActivity(url)
        } else {
            activity.text?.let { text ->
                val textArr = text.split(" ")
                when(textArr[commandIndex].toLowerCase()) {
                    Commands.WELCOME.text -> sendWelcomeActivity(url)
                    Commands.HELP.text -> sendHelpActivity(url)
                    Commands.CHECK.text -> sendHelpActivity(url)
                    Commands.SEND_ME_A_DUCK.text -> sendDuckActivity(url)
                    else -> sendDefaultActivity(url, textArr[commandIndex])
                }
            }
        }
    }

    private fun sendWelcomeActivity(url: String) {
        val body = objectMapper.writeValueAsString(Activity(
            type = ActivityTypes.MESSAGE.type,
            attachments = listOf(Attachment(
                contentType = AttachmentContentTypes.HERO.type,
                content = HeroCard(
                    title = "Welcome to NotifyBot!",
                    text = "This bot sends notifications at a specified time. Send \"help\" to see the list of available commands"
                )
            ))
        ))

        val headers = mapOf("Authorization" to "Bearer ${authenticationService.getToken()}")

        HttpClient(url).post(body, headers)
    }

    private fun sendHelpActivity(url: String) {
        val body = objectMapper.writeValueAsString(Activity(
            type = ActivityTypes.MESSAGE.type,
            attachments = listOf(Attachment(
                contentType = AttachmentContentTypes.HERO.type,
                content = HeroCard(
                    title = "Available commands",
                    text = Commands.values().joinToString(", ") { it.text }
                )
            ))
        ))

        val headers = mapOf("Authorization" to "Bearer ${authenticationService.getToken()}")

        HttpClient(url).post(body, headers)
    }

    private fun sendDuckActivity(url: String) {
        val body = objectMapper.writeValueAsString(Activity(
            type = ActivityTypes.MESSAGE.type,
            attachments = listOf(Attachment(
                contentType = AttachmentContentTypes.HERO.type,
                content = HeroCard(
                    title = "Duck On A Rock",
                    text = "Quaq-quaq!",
                    images = listOf(CardImage(
                        alt = "picture of a duck",
                        url = "https://aka.ms/DuckOnARock"
                    ))
                )
            ))
        ))

        val headers = mapOf("Authorization" to "Bearer ${authenticationService.getToken()}")

        HttpClient(url).post(body, headers)
    }

    private fun sendDefaultActivity(url: String, command: String) {
        val body = objectMapper.writeValueAsString(Activity(
            type = ActivityTypes.MESSAGE.type,
            text = "Unknown command: $command"
        ))

        val headers = mapOf("Authorization" to "Bearer ${authenticationService.getToken()}")

        HttpClient(url).post(body, headers)
    }
}
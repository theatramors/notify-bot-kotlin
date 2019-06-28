package com.amors.notifybot.services

import com.amors.notifybot.dto.Activity
import com.amors.notifybot.dto.Attachment
import com.amors.notifybot.dto.CardImage
import com.amors.notifybot.dto.ChannelAccount
import com.amors.notifybot.dto.HeroCard
import com.amors.notifybot.enums.ActivityTypes
import com.amors.notifybot.enums.AttachmentContentTypes
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
                    "welcome" -> sendWelcomeActivity(url)
                    "help" -> sendHelpActivity(url)
                    "members" -> sendMembersList(activity)
                    "sendmeaduck" -> sendDuckActivity(url)
                    else -> {
                        val body = objectMapper.writeValueAsString(Activity(
                            type = ActivityTypes.MESSAGE.type,
                            text = "Unknown command: ${textArr[commandIndex]}"
                        ))

                        val headers = mapOf("Authorization" to "Bearer ${authenticationService.getToken()}")

                        HttpClient(url).post(body, headers)
                    }
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
                    text = "welcome, help, sendmeaduck"
                )
            ))
        ))

        val headers = mapOf("Authorization" to "Bearer ${authenticationService.getToken()}")

        HttpClient(url).post(body, headers)
    }

    private fun sendMembersList(activity: Activity) {
        val conversation = activity.conversation!!
        val url = "${activity.serviceUrl}v3/conversations/${conversation.id}/members"
        val headers = mapOf("Authorization" to "Bearer ${authenticationService.getToken()}")

        val members = objectMapper.readValue<List<ChannelAccount>>(HttpClient(url).get(headers))

        HttpClient("${activity.serviceUrl}v3/conversations/${conversation.id}/activities").post(objectMapper.writeValueAsString(Activity(
            type = ActivityTypes.MESSAGE.type,
            text = members.joinToString(",") { "${it.id}" }
        )), headers)
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
}
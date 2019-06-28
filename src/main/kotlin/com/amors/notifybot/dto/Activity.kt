package com.amors.notifybot.dto

import java.time.LocalDateTime

data class Activity(
    val action: String? = null,
    val attachments: List<Attachment>? = emptyList(),
    val channelId: String? = null,
    val conversation: ConversationAccount? = null,
    val from: ChannelAccount? = null,
    val id: String? = null,
    val name: String? = null,
    val recipient: ChannelAccount? = null,
    val serviceUrl: String? = null,
    val text: String? = null,
    val timestamp: LocalDateTime? = null,
    val type: String? = null
)
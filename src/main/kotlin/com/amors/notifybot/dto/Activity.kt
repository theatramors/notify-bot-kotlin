package com.amors.notifybot.dto

import java.time.LocalDateTime

data class Activity(
    val action: String? = null,
    val type: String,
    val text: String?,
    val serviceUrl: String? = null,
    val timestamp: LocalDateTime? = null,
    val from: ChannelAccount? = null,
    val conversation: ConversationAccount? = null,
    val recipient: ChannelAccount? = null
)
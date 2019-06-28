package com.amors.notifybot.dto

data class HeroCard(
    val title: String? = null,
    val subtitle: String? = null,
    val text: String? = null,
    val images: List<CardImage>? = emptyList()
)
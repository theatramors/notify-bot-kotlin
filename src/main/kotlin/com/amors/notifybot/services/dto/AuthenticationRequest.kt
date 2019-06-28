package com.amors.notifybot.services.dto

import com.fasterxml.jackson.annotation.JsonProperty

@Suppress("unused")
data class AuthenticationRequest(

    @JsonProperty(value = "client_id")
    val clientId: String,

    @JsonProperty(value = "client_secret")
    val clientSecret: String,

    @JsonProperty(value = "grant_type")
    val grantType: String,

    @JsonProperty(value = "scope")
    val scope: String
)
package com.amors.notifybot.services.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthenticationResponse(

    @JsonProperty(value = "token_type")
    val tokenType: String,

    @JsonProperty(value = "expires_in")
    val expiresIn: Long,

    @JsonProperty(value = "ext_expires_in")
    val extExpiresIn: Long,

    @JsonProperty(value = "access_token")
    val accessToken: String
)
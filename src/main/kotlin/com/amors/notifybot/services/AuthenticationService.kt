package com.amors.notifybot.services

import com.amors.notifybot.config.BotProperties
import com.amors.notifybot.http.HttpClient
import com.amors.notifybot.services.dto.AuthenticationResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val objectMapper: ObjectMapper,
    private val botProperties: BotProperties
) {

    /**
     * Get authentication with Microsoft credentials
     * and return token from authentication data
     *
     * @return token as string
     */
    fun getToken(): String {
        return authenticate().accessToken
    }

    /**
     * Authenticates with Microsoft credentials
     *
     * @return authentication data
     */
    fun authenticate(): AuthenticationResponse {
        val url = botProperties.tokenUrl

        val clientId = botProperties.clientId
        val clientSecret = botProperties.clientSecret
        val grantType = botProperties.grantType
        val scope = botProperties.scope

        val body = "client_id=$clientId&client_secret=$clientSecret&grant_type=$grantType&scope=$scope"

        val headers = mapOf("Content-Type" to "application/x-www-form-urlencoded")

        return objectMapper.readValue(HttpClient(url).post(body, headers), AuthenticationResponse::class.java)
    }
}
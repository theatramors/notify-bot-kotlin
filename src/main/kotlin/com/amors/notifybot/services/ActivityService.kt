package com.amors.notifybot.services

import com.amors.notifybot.dto.Activity
import com.amors.notifybot.http.HttpClient
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class ActivityService(private val objectMapper: ObjectMapper) {

    /**
     * Processing Activity
     */
    fun processActivity(activity: Activity) {
        val url = "${activity.serviceUrl}v3/conversations/${activity.conversation!!.id}/activities"
        val body = objectMapper.writeValueAsString(Activity(type = "message", text = "You say: ${activity.text}"))
        val headers = mapOf(
            "Authorization" to "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkN0ZlFDOExlLThOc0M3b0MyelFrWnBjcmZPYyIsImtpZCI6IkN0ZlFDOExlLThOc0M3b0MyelFrWnBjcmZPYyJ9.eyJhdWQiOiJodHRwczovL2FwaS5ib3RmcmFtZXdvcmsuY29tIiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvZDZkNDk0MjAtZjM5Yi00ZGY3LWExZGMtZDU5YTkzNTg3MWRiLyIsImlhdCI6MTU2MTY3NDQ1OCwibmJmIjoxNTYxNjc0NDU4LCJleHAiOjE1NjE2NzgzNTgsImFpbyI6IjQyWmdZRkNzY2pMWGY4M1NtREhuNDV5VEVrM1dBQT09IiwiYXBwaWQiOiJjZWE2YzI2OC0xZDlhLTQyZjktYTY0OS05ODc5YzQwNWM2MjciLCJhcHBpZGFjciI6IjEiLCJpZHAiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9kNmQ0OTQyMC1mMzliLTRkZjctYTFkYy1kNTlhOTM1ODcxZGIvIiwidGlkIjoiZDZkNDk0MjAtZjM5Yi00ZGY3LWExZGMtZDU5YTkzNTg3MWRiIiwidXRpIjoiRlo0LUU0VXhMVVdrODBMSWdSUWdBQSIsInZlciI6IjEuMCJ9.APAYhMJUcx9cyKwtAtRG-suh5kfx5GT7Rv5Y4B3MQYHHgCl6wAXBX5h3G_VoHLhHBmB8Tz6wEj3T5n0xZwdrQ06shujIutW1HI1ZLkmc-32HEguWGJdoRGuUeTMuaL5jqI96qVs3n_itDcftaJJHSMSwu9XkORCoH_F4DUNW7I60aHAnNAQ5MyhykHqDs1Pvs6kXkFfNF2_lNS8pzC2BrK9rt8Y7L3kebRXE4xJmKAxbI-bqYfR_I5xHfH9DS-fcUU0XKk6ZePEnkRqA8bqV9TJdb9mZPgiNuO7GHjQ2GT_Dt5jfdLXQJcodQfNWC0bAKCTPkCd7mGw6MBrqRvNJTw"
        )

        HttpClient(url).post(body, headers)
    }
}
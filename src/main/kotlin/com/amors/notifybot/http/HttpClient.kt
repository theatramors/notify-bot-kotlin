package com.amors.notifybot.http

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpClient(private val url: String) {

    private val logger: Logger = LoggerFactory.getLogger(HttpClient::class.java)

    fun post(body: String) {
        logger.info("Sending POST request to $url")

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doInput = true
        connection.doOutput = true

        logger.info("Request body is $body")

        connection.outputStream.use { outputStream ->
            outputStream.write(body.toByteArray(Charsets.UTF_8), 0, body.length)
        }

        BufferedReader(InputStreamReader(connection.inputStream)).use { bufferedReader ->
            val response = StringBuilder()
            bufferedReader.lines().forEach { line ->
                response.append(line.trim { it <= ' ' })
            }
            logger.info("Request response is $response")
        }

        connection.connect()
    }
}
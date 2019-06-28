package com.amors.notifybot.http

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Class for HTTP requests
 */
class HttpClient(private val url: String) {

    private val logger: Logger = LoggerFactory.getLogger(HttpClient::class.java)

    /**
     * Makes HTTP GET request
     *
     * @param headers HTTP headers
     * @return response body as string
     */
    fun get(headers: Map<String, String> = emptyMap()): String {
        logger.info("Sending GET request to $url")

        val connection = URL(url).openConnection() as HttpURLConnection

        headers.forEach { connection.setRequestProperty(it.key, it.value) }

        val inputStream = try {
            connection.inputStream
        } catch (ex: IOException) {
            connection.errorStream
        }

        BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
            val response = StringBuilder()
            bufferedReader.lines().forEach { line ->
                response.append(line.trim { it <= ' ' })
            }
            logger.info("Request response is $response")
            return response.toString()
        }
    }

    /**
     * Makes HTTP POST request
     *
     * @param body request body
     * @param headers HTTP headers
     * @return response body as string
     */
    fun post(body: String, headers: Map<String, String> = emptyMap()): String {
        logger.info("Sending POST request to $url")

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doInput = true
        connection.doOutput = true

        headers.forEach { connection.setRequestProperty(it.key, it.value) }

        logger.info("Request body is $body")

        connection.outputStream.use { outputStream ->
            outputStream.write(body.toByteArray(Charsets.UTF_8), 0, body.length)
        }

        val inputStream = try {
            connection.inputStream
        } catch (ex: IOException) {
            connection.errorStream
        }

        BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
            val response = StringBuilder()
            bufferedReader.lines().forEach { line ->
                response.append(line.trim { it <= ' ' })
            }
            logger.info("Request response is $response")
            return response.toString()
        }
    }
}
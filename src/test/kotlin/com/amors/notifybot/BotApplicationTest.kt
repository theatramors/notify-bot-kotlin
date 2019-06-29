package com.amors.notifybot

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@ActiveProfiles("test-beta")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [BotApplication::class])
class BotApplicationTest {

    @Test
    fun contextLoadSuccess() {
        Assert.assertTrue(true)
    }
}
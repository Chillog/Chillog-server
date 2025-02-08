package com.chatbot.domain.analyze.service

import com.chatbot.domain.analyze.exception.AnalyzeErrorCode
import com.chatbot.domain.user.entity.UserEntity
import com.chatbot.global.exception.CustomException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit

@Service
class AnalyzeService (
    private val objectMapper: ObjectMapper,

    @Value("\${spring.ai.openai.api-key}")
    private val apiKey: String,

    @Value("\${spring.ai.openai.assistantId}")
    private val assistantId: String,

    private val redisTemplate: StringRedisTemplate
) {
    private val restTemplate = RestTemplate()
    private val apiUrl = "https://api.openai.com/v1/threads"

    val headers = HttpHeaders().apply {
        set("Authorization", "Bearer $apiKey")
        set("Content-Type", "application/json")
        set("OpenAI-Beta", "assistants=v2")
    }

    fun analyze(user: UserEntity): String {
        val threadId = createThread()

        createMessage(threadId, user.score)
        runAssistant(threadId)
        sleep(3000)
        return getMessage(user.id.toString(), threadId)
    }

    // thread 발급
    fun createThread(): String {
        val entity = HttpEntity<String>(headers)
        val response: ResponseEntity<String> = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String::class.java)
        val rootNode: JsonNode = objectMapper.readTree(response.body)
        return rootNode["id"].asText()
    }

    // 메시지 생성
    fun createMessage(threadId: String, score: Int) {
        val requestBody = mapOf(
            "role" to "user",
            "content" to score.toString()
        )
        val entity = HttpEntity(requestBody,headers)
        restTemplate.exchange("$apiUrl/$threadId/messages", HttpMethod.POST, entity, String::class.java)
    }

    // assistant 실행하기
    fun runAssistant(threadId: String) {
        val requestBody = mapOf(
            "assistant_id" to assistantId,
        )
        val entity = HttpEntity(requestBody,headers)
        restTemplate.exchange("$apiUrl/$threadId/runs", HttpMethod.POST, entity, String::class.java)
    }

    // 메시지 가져오기
    fun getMessage(userId: String, threadId: String): String{
        while (true) {
            try {
                val entity = HttpEntity<String>(headers)
                val response =
                    restTemplate.exchange("$apiUrl/$threadId/messages", HttpMethod.GET, entity, String::class.java)
                val responseBody = objectMapper.readValue<Map<String, Any>>(response.body!!)

                val data = responseBody["data"] as? List<Map<String, Any>>
                val firstMessage = data?.firstOrNull()

                val role = firstMessage?.get("role") as? String ?: ""
                val contentList = firstMessage?.get("content") as? List<Map<String, Any>>

                val value = contentList?.firstOrNull()?.let { contentItem ->
                    val textMap = contentItem["text"] as? Map<String, Any>
                    textMap?.get("value") as? String
                }

                if (role == "user" && value != null) {
                    sleep(1000) // 제일 처음 메시자가 user이면 기다렸다가 다시 요청 보내기
                } else {
                    redisTemplate.opsForValue().set("analyze:$", value!!, 1, TimeUnit.DAYS)
                    return value
                }
            } catch (e: Exception) {
                log.info(e.message)
                throw CustomException(AnalyzeErrorCode.GET_CHAT_ERROR)
            }
        }
    }

    fun getHistory(userId: String): String {
        return redisTemplate.opsForValue().get("analyze:$userId")
            ?: throw throw CustomException(AnalyzeErrorCode.RESPONSE_IS_NULL)
    }
}
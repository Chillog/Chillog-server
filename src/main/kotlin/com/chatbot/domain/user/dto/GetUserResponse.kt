package com.chatbot.domain.user.dto

data class GetUserResponse (
    val id: Long,

    val username: String,

    val score: Int,
    val point: Int
)
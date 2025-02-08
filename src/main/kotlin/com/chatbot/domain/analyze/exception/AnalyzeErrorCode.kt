package com.chatbot.domain.analyze.exception

import com.chatbot.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class AnalyzeErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {
    GET_CHAT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "채팅을 가져오는 과정에서 오류가 발생하였습니다."),
    RESPONSE_IS_NULL(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "분석 결과가 없습니다.")
}
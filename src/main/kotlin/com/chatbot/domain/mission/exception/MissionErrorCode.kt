package com.chatbot.domain.mission.exception

import com.chatbot.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class MissionErrorCode(
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {
    MISSION_NOT_FOUND(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "미션을 찾을 수 없습니다.")
}

package com.chatbot.domain.mission.service

import com.chatbot.domain.mission.entity.MissionEntity
import com.chatbot.domain.mission.exception.MissionErrorCode
import com.chatbot.domain.mission.repository.MissionRepository
import com.chatbot.domain.user.exception.UserErrorCode
import com.chatbot.domain.user.repository.UserRepository
import com.chatbot.global.dto.BaseResponse
import com.chatbot.global.exception.CustomException
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class MissionService (
    private val userRepository: UserRepository,
    private val missionRepository: MissionRepository
) {

    fun listMissions(): List<MissionEntity> {
        return missionRepository.findAll()
    }

    fun clearMissions(principal: Principal, missionId: Long): BaseResponse<Unit> {
        val user = userRepository.findByUsername(principal.name).orElseThrow{ CustomException(UserErrorCode.USER_NOT_FOUND) }
        val mission = missionRepository.findById(missionId).orElseThrow{ CustomException(MissionErrorCode.MISSION_NOT_FOUND) }

        user.point += mission.intensity
        userRepository.save(user)

        return BaseResponse(
            message = "유저 포인트가 ${mission.intensity} 올랐습니다."
        )
    }
}
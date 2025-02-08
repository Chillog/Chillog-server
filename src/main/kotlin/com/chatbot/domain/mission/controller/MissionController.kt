package com.chatbot.domain.mission.controller

import com.chatbot.domain.mission.entity.MissionEntity
import com.chatbot.domain.mission.service.MissionService
import com.chatbot.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Mission", description = "미션")
@RestController
@RequestMapping("/missions")
class MissionController (
    private val missionService: MissionService
) {
    @Operation(summary = "추천 미션")
    @GetMapping("/list")
    fun listMissions(): List<MissionEntity>{
        return missionService.listMissions()
    }


    @Operation(summary = "미션 클리어")
    @GetMapping("/clear")
    fun clearMissions(principal: Principal, @RequestParam missionId: Long): BaseResponse<Unit> {
        return missionService.clearMissions(principal, missionId)
    }
}
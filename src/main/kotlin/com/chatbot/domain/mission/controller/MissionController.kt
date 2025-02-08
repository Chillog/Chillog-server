package com.chatbot.domain.mission.controller

import com.chatbot.domain.mission.service.MissionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Mission", description = "미션")
@RestController
@RequestMapping("/missions")
class MissionController (
    private val missionService: MissionService
) {
    @Operation(summary = "추천 미션")
    @GetMapping("/list")
    fun listMissions(): String{
        return missionService.listMissions()
    }
}
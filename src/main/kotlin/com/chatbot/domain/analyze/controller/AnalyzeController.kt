package com.chatbot.domain.analyze.controller

import com.chatbot.domain.analyze.service.AnalyzeService
import com.chatbot.domain.user.entity.UserEntity
import com.chatbot.domain.user.exception.UserErrorCode
import com.chatbot.domain.user.repository.UserRepository
import com.chatbot.global.exception.CustomException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Tag(name = "Analyze", description = "분석 기능")
@RestController
@RequestMapping("/analyze")
class AnalyzeController (
    private val analyzeService: AnalyzeService,
    private val userRepository: UserRepository
) {
    @Operation(summary = "분석 받기")
    @PostMapping("/send")
    fun analyze(principal: Principal): String {
        val user: UserEntity = userRepository.findByUsername(principal.name).orElseThrow{ CustomException(UserErrorCode.USER_NOT_FOUND) }
        return analyzeService.analyze(user)
    }

    @Operation(summary = "분석 조회하기")
    @GetMapping
    fun getHistory(principal: Principal): String? {
        val user: UserEntity = userRepository.findByUsername(principal.name).orElseThrow{ CustomException(UserErrorCode.USER_NOT_FOUND) }
        return analyzeService.getHistory(user.id.toString())
    }
}
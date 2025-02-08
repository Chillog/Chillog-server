package com.chatbot.domain.user.controller

import com.chatbot.domain.user.dto.GetUserResponse
import com.chatbot.domain.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "User", description = "유저 관련")
@RestController
@RequestMapping("/users")
class UserController (
    private val userService: UserService
) {
    @Operation(summary = "마이페이지")
    @GetMapping("/me")
    fun getMe(principal: Principal): GetUserResponse {
        return userService.getMe(principal)
    }
}
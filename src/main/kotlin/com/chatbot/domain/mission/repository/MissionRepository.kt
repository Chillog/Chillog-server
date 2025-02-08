package com.chatbot.domain.mission.repository

import com.chatbot.domain.mission.entity.MissionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MissionRepository : JpaRepository<MissionEntity, Long> {

}
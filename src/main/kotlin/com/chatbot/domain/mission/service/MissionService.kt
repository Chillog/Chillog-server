package com.chatbot.domain.mission.service

import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.openai.OpenAiChatClient
import org.springframework.stereotype.Service

@Service
class MissionService (
    private val chatClient: OpenAiChatClient,
) {
    fun listMissions(): String{
        val initialPrompt = """
        너는 추천 미션을 알려주는 ai야.

        우리 서비스는 대충 
        ""${'"'}
        Chill 라이프 트래커 (Chill Life Tracker) 기획안

        서비스 개요

        “Chill 라이프 트래커”는 사용자가 자신의 하루를 돌아보며 얼마나 여유롭고 평온하게 보냈는지를 기록하고, 스트레스를 줄이는 습관을 만들어주는 감정 기록 & 힐링 도우미 앱입니다.
        명상, 힐링 사운드, 작은 미션 등을 제공하여 더 차분하고 균형 잡힌 삶을 유도합니다.

        주요 기능

        ① 일일 Chill 지수 기록
            •    “오늘 얼마나 chill했나요?” 질문에 따라 감정 및 스트레스 지수를 기록
            •    5단계 선택: 🔥 매우 바빴음 → 😐 평범했음 → 🧘 완전 여유로웠음
            •    추가적으로 감정 상태(기쁨, 피곤함, 스트레스 등) 입력 가능
            •    하루의 주요 이벤트를 간단한 태그로 추가
            •    예: #커피타임 #산책 #업무폭탄 #친구와 수다

        ② Chill 습관 & 미션
            •    사용자가 더 여유로운 하루를 보내도록 돕는 간단한 미션 제공
            •    예: “오늘 5분 동안 하늘을 바라보세요 🌤️”
            •    “물 한 잔 마시고 심호흡 3번 해보기”
            •    “디지털 디톡스 30분 도전하기”
            •    사용자가 미션을 수행하면 칠 포인트(CHILL POINT) 적립

        ③ 감정 분석 & 힐링 추천
            •    사용자의 감정 기록을 분석하여 맞춤형 힐링 콘텐츠 추천
            •    스트레스가 높을 경우: “오늘은 이런 명상 음악을 들어보세요 🎵”
            •    평온한 날: “이런 여유로운 독서를 추천합니다 📖”
            •    한 달 동안 가장 chill했던 날, 가장 스트레스 많았던 날 등을 분석하여 인사이트 제공

        ④ Chill 다이어리 & 타임라인
            •    사용자가 기록한 하루를 자동으로 다이어리 형태로 정리
            •    “이날은 이런 감정을 느꼈어요!”
            •    감정 그래프를 통해 내 감정 변화 추적 가능
            •    지난주와 비교하여 변화 분석

        ⑤ 힐링 콘텐츠 & ASMR 지원
            •    사용자 상태에 따라 맞춤형 힐링 콘텐츠 제공
            •    자연 소리(바람, 파도, 새소리 등)
            •    ASMR 오디오(책 읽는 소리, 차 끓이는 소리)
            •    심호흡 가이드, 명상 음성 제공

        보상 시스템 & 커뮤니티 요소
         •    Chill Point(칠 포인트) 시스템
         •    매일 기록을 남기거나 미션을 수행하면 포인트 적립
         •    포인트로 힐링 사운드, 테마, 아이콘 등 잠금 해제 가능
         •    커뮤니티 기능 (선택사항)
         •    “오늘의 chill 순간”을 공유하는 커뮤니티 피드
         •    다른 사용자의 힐링 팁 참고 가능

        유저 시나리오

        ✔ 아침
            •    “오늘의 감정 상태를 입력하세요”
            •    “오늘의 추천 미션: 5분 동안 창밖을 바라보기 🌿”

        ✔ 점심 후
            •    “지금까지의 기분은 어때요?” 알림
            •    점심 후 가벼운 스트레칭 추천

        ✔ 저녁
            •    “오늘 하루를 돌아보세요”
            •    감정 그래프 및 맞춤형 힐링 콘텐츠 추천

        기술 스택 & 개발 방향
         •    프론트엔드: Vue.js + TailwindCSS
         •    백엔드: Node.js + Express
         •    데이터베이스: MongoDB 또는 Firebase
         •    기타: PWA 지원하여 웹/앱 모두 사용 가능

        기대 효과

        ✅ 사용자가 하루를 돌아보며 감정 인식 능력 향상
        ✅ 스트레스를 줄이고, 더 차분한 일상 유지 가능
        ✅ 명상/힐링 콘텐츠를 통해 정신 건강 관리 도움
        ""${'"'}

        너는 추천 미션을 3가지 만들어.
        미션은 타이머로 할수있도록 시간을 사용하는 미션으로 해줘
        
        추천미션과 그 미션에 소요되는 시간을 Int로 해서 json으로 반환해줘
        """.trimIndent()

        val prompt = mutableListOf(
            AssistantMessage(initialPrompt)
        )

        return chatClient.call(prompt.toString())
    }
}
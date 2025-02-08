package com.chatbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [
    org.springframework.cloud.function.context.config.ContextFunctionCatalogAutoConfiguration::class
])
class chatbotApplication

fun main(args: Array<String>) {
    runApplication<chatbotApplication>(*args)
}

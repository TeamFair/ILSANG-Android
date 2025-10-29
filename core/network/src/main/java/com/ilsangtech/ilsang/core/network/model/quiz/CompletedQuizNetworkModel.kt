package com.ilsangtech.ilsang.core.network.model.quiz

import kotlinx.serialization.Serializable

@Serializable
data class CompletedQuizNetworkModel(
    val id: Int,
    val answers: List<QuizAnswerNetworkModel>,
    val question: String?,
    val userAnswer: String?,
    val createdAt: String?
)
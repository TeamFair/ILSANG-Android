package com.ilsangtech.ilsang.core.network.model.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizAnswerNetworkModel(
    val id: Int,
    val answer: String,
    val sortOrder: Int,
    val quizId: Int,
    val createdAt: String?,
    val createdBy: String?,
    val updatedAt: String?,
    val updatedBy: String?
)
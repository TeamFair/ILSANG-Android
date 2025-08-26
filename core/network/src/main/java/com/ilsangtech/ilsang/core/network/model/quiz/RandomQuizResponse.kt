package com.ilsangtech.ilsang.core.network.model.quiz

import kotlinx.serialization.Serializable

@Serializable
data class RandomQuizResponse(
    val id: Int,
    val question: String,
    val hint: String?
)
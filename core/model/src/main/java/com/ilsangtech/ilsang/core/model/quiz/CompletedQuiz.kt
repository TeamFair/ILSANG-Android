package com.ilsangtech.ilsang.core.model.quiz

data class CompletedQuiz(
    val id: Int,
    val question: String?,
    val answer: String?,
    val userAnswer: String?
)

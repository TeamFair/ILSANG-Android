package com.ilsangtech.ilsang.core.data.quiz.mapper

import com.ilsangtech.ilsang.core.model.quiz.CompletedQuiz
import com.ilsangtech.ilsang.core.network.model.quiz.CompletedQuizNetworkModel

internal fun CompletedQuizNetworkModel.toCompletedQuiz(): CompletedQuiz {
    return CompletedQuiz(
        id = id,
        question = question,
        answer = answers.firstOrNull()?.answer,
        userAnswer = userAnswer,
    )
}
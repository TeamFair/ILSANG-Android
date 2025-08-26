package com.ilsangtech.ilsang.core.data.quiz.mapper

import com.ilsangtech.ilsang.core.model.quiz.RandomQuiz
import com.ilsangtech.ilsang.core.network.model.quiz.RandomQuizResponse

internal fun RandomQuizResponse.toRandomQuiz(): RandomQuiz {
    return RandomQuiz(
        id = id,
        question = question,
        hint = hint
    )
}
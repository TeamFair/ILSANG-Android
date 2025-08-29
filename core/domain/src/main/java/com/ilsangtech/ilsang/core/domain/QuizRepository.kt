package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.quiz.RandomQuiz
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getRandomQuiz(missionId: Int): Flow<RandomQuiz>
}
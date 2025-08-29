package com.ilsangtech.ilsang.core.data.quiz.datasource

import com.ilsangtech.ilsang.core.network.model.quiz.RandomQuizResponse

interface QuizDataSource {
    suspend fun getRandomQuiz(missionId: Int): RandomQuizResponse
}
package com.ilsangtech.ilsang.core.data.quiz.datasource

import com.ilsangtech.ilsang.core.network.api.QuizApiService
import com.ilsangtech.ilsang.core.network.model.quiz.RandomQuizResponse

class QuizDataSourceImpl(
    private val quizApiService: QuizApiService
) : QuizDataSource {
    override suspend fun getRandomQuiz(missionId: Int): RandomQuizResponse {
        return quizApiService.getRandomQuiz(missionId)
    }
}
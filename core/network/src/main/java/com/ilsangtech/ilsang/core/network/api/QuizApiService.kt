package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.quiz.RandomQuizResponse
import retrofit2.http.GET

interface QuizApiService {
    @GET("api/v1/challenge/random-quiz")
    suspend fun getRandomQuiz(missionId: Int): RandomQuizResponse
}
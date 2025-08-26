package com.ilsangtech.ilsang.core.data.quiz.repository

import com.ilsangtech.ilsang.core.data.quiz.datasource.QuizDataSource
import com.ilsangtech.ilsang.core.data.quiz.mapper.toRandomQuiz
import com.ilsangtech.ilsang.core.domain.QuizRepository
import com.ilsangtech.ilsang.core.model.quiz.RandomQuiz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuizRepositoryImpl(
    private val quizDataSource: QuizDataSource
) : QuizRepository {
    override fun getRandomQuiz(missionId: Int): Flow<RandomQuiz> {
        return flow {
            emit(quizDataSource.getRandomQuiz(missionId).toRandomQuiz())
        }
    }
}
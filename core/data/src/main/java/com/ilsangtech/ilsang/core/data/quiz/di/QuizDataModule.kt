package com.ilsangtech.ilsang.core.data.quiz.di

import com.ilsangtech.ilsang.core.data.quiz.datasource.QuizDataSource
import com.ilsangtech.ilsang.core.data.quiz.datasource.QuizDataSourceImpl
import com.ilsangtech.ilsang.core.data.quiz.repository.QuizRepositoryImpl
import com.ilsangtech.ilsang.core.domain.QuizRepository
import com.ilsangtech.ilsang.core.network.api.QuizApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizDataModule {
    @Provides
    @Singleton
    fun providesQuizDataSource(quizApiService: QuizApiService): QuizDataSource {
        return QuizDataSourceImpl(quizApiService)
    }

    @Provides
    @Singleton
    fun providesQuizRepository(quizDataSource: QuizDataSource): QuizRepository {
        return QuizRepositoryImpl(quizDataSource)
    }
}
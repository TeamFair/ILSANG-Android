package com.ilsangtech.ilsang.core.data.quest.di

import com.ilsangtech.ilsang.core.data.quest.datasource.QuestDataSource
import com.ilsangtech.ilsang.core.data.quest.datasource.QuestDataSourceImpl
import com.ilsangtech.ilsang.core.data.quest.repository.QuestCompleteDateRepositoryImpl
import com.ilsangtech.ilsang.core.data.quest.repository.QuestRepositoryImpl
import com.ilsangtech.ilsang.core.domain.QuestCompleteDateRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.network.api.QuestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuestDataModule {
    @Provides
    @Singleton
    fun provideQuestDataSource(questApiService: QuestApiService): QuestDataSource {
        return QuestDataSourceImpl(questApiService)
    }

    @Provides
    @Singleton
    fun provideQuestRepository(
        questDataSource: QuestDataSource,
    ): QuestRepository {
        return QuestRepositoryImpl(questDataSource)
    }

    @Provides
    @Singleton
    fun provideQuestCompleteDateRepository(): QuestCompleteDateRepository {
        return QuestCompleteDateRepositoryImpl()
    }
}
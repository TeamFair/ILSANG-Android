package com.ilsangtech.ilsang.core.data.emoji.di

import com.ilsangtech.ilsang.core.data.emoji.datasource.EmojiDataSource
import com.ilsangtech.ilsang.core.data.emoji.datasource.EmojiDataSourceImpl
import com.ilsangtech.ilsang.core.data.emoji.repository.EmojiRepositoryImpl
import com.ilsangtech.ilsang.core.domain.EmojiRepository
import com.ilsangtech.ilsang.core.network.api.EmojiApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object EmojiModule {
    @Provides
    @Singleton
    fun provideEmojiDataSource(emojiApiService: EmojiApiService): EmojiDataSource {
        return EmojiDataSourceImpl(emojiApiService)
    }

    @Provides
    @Singleton
    fun provideEmojiRepository(
        emojiDataSource: EmojiDataSource
    ): EmojiRepository {
        return EmojiRepositoryImpl(emojiDataSource)
    }
}
package com.ilsangtech.ilsang.core.data.emoji.di

import com.ilsangtech.ilsang.core.data.emoji.datasource.EmojiDataSource
import com.ilsangtech.ilsang.core.data.emoji.datasource.EmojiDataSourceImpl
import com.ilsangtech.ilsang.core.data.emoji.repository.EmojiRepositoryImpl
import com.ilsangtech.ilsang.core.domain.EmojiRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.network.api.EmojiApiService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object EmojiModule {
    fun provideEmojiDataSource(emojiApiService: EmojiApiService): EmojiDataSource {
        return EmojiDataSourceImpl(emojiApiService)
    }

    fun provideEmojiRepository(
        userRepository: UserRepository,
        emojiDataSource: EmojiDataSource
    ): EmojiRepository {
        return EmojiRepositoryImpl(
            userRepository = userRepository,
            emojiDataSource = emojiDataSource
        )
    }
}
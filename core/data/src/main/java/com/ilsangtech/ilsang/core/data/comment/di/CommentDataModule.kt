package com.ilsangtech.ilsang.core.data.comment.di

import com.ilsangtech.ilsang.core.data.comment.datasource.CommentDataSource
import com.ilsangtech.ilsang.core.data.comment.datasource.CommentDataSourceImpl
import com.ilsangtech.ilsang.core.network.api.CommentApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CommentDataModule {
    @Provides
    fun provideCommentDataSource(apiService: CommentApiService): CommentDataSource {
        return CommentDataSourceImpl(apiService)
    }
}
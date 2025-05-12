package com.ilsangtech.ilsang.core.network.di

import com.ilsangtech.ilsang.core.network.BuildConfig
import com.ilsangtech.ilsang.core.network.api.AuthApiService
import com.ilsangtech.ilsang.core.network.api.BannerApiService
import com.ilsangtech.ilsang.core.network.api.ChallengeApiService
import com.ilsangtech.ilsang.core.network.api.EmojiApiService
import com.ilsangtech.ilsang.core.network.api.ImageApiService
import com.ilsangtech.ilsang.core.network.api.QuestApiService
import com.ilsangtech.ilsang.core.network.api.RankApiService
import com.ilsangtech.ilsang.core.network.api.UserApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                ).build())
            .baseUrl("${BuildConfig.SERVER_URL}/api/")
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideBannerApiService(retrofit: Retrofit): BannerApiService {
        return retrofit.create(BannerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuestApiService(retrofit: Retrofit): QuestApiService {
        return retrofit.create(QuestApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRankApiService(retrofit: Retrofit): RankApiService {
        return retrofit.create(RankApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideChallengeApiService(retrofit: Retrofit): ChallengeApiService {
        return retrofit.create(ChallengeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageApiService(retrofit: Retrofit): ImageApiService {
        return retrofit.create(ImageApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEmojiApiService(retrofit: Retrofit): EmojiApiService {
        return retrofit.create(EmojiApiService::class.java)
    }
}
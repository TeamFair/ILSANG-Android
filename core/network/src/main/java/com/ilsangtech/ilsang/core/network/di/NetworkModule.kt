package com.ilsangtech.ilsang.core.network.di

import com.ilsangtech.ilsang.core.network.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${BuildConfig.SERVER_URL}/api/")
            .addConverterFactory(
                Json.asConverterFactory(MediaType.get("application/json"))
            )
            .build()
    }
}
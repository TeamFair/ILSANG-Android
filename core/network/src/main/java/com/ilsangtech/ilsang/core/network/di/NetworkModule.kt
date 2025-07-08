package com.ilsangtech.ilsang.core.network.di

import com.ilsangtech.ilsang.core.datastore.UserDataStore
import com.ilsangtech.ilsang.core.network.BuildConfig
import com.ilsangtech.ilsang.core.network.api.AuthApiService
import com.ilsangtech.ilsang.core.network.api.BannerApiService
import com.ilsangtech.ilsang.core.network.api.ChallengeApiService
import com.ilsangtech.ilsang.core.network.api.EmojiApiService
import com.ilsangtech.ilsang.core.network.api.ImageApiService
import com.ilsangtech.ilsang.core.network.api.QuestApiService
import com.ilsangtech.ilsang.core.network.api.RankApiService
import com.ilsangtech.ilsang.core.network.api.UserApiService
import com.ilsangtech.ilsang.core.network.model.auth.OAuthRefreshRequest
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
 import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideAuthenticator(
        userDataStore: UserDataStore,
        authApiService: AuthApiService
    ): Authenticator {
        return object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                if (getResponseCount(response) >= 2) return null
                return runBlocking {
                    val accessToken =
                        userDataStore.accessToken.first()
                            ?: throw Exception("access token is null")
                    val refreshToken =
                        userDataStore.refreshToken.first()
                            ?: throw Exception("refresh token is null")

                    val tokenResponse = try {
                        authApiService.oAuthRefresh(
                            OAuthRefreshRequest(
                                accessToken = accessToken,
                                refreshToken = refreshToken
                            )
                        )
                    } catch (e: Exception) {
                        return@runBlocking null
                    }

                    userDataStore.setAccessToken(tokenResponse.data.authorization)
                    tokenResponse.data.refreshToken?.let { token ->
                        userDataStore.setRefreshToken(token)
                    }

                    response.request.newBuilder()
                        .header("Authorization", tokenResponse.data.authorization)
                        .build()
                }
            }

            fun getResponseCount(response: Response?): Int {
                var result = 1
                var r = response?.priorResponse
                while (r != null) {
                    result += 1
                    r = r.priorResponse
                }
                return result
            }
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(userDataStore: UserDataStore): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val token = runBlocking {
                userDataStore.accessToken.first()
            } ?: return@Interceptor chain.proceed(original)

            val newRequest = original.newBuilder()
                .header("Authorization", token)
                .build()
            chain.proceed(newRequest)
        }
    }

    @AuthOkHttpClient
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(
        authenticator: Authenticator,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    @BaseOkHttpClient
    @Provides
    @Singleton
    fun provideBaseOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideAuthRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("${BuildConfig.SERVER_URL}/api/")
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    @BaseOkHttpClient
    fun provideBaseRetrofit(@BaseOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        val json = Json { encodeDefaults = true }
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("${BuildConfig.SERVER_URL}/api/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideBannerApiService(@AuthOkHttpClient retrofit: Retrofit): BannerApiService {
        return retrofit.create(BannerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuestApiService(@AuthOkHttpClient retrofit: Retrofit): QuestApiService {
        return retrofit.create(QuestApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(@BaseOkHttpClient retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRankApiService(@AuthOkHttpClient retrofit: Retrofit): RankApiService {
        return retrofit.create(RankApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(@AuthOkHttpClient retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideChallengeApiService(@AuthOkHttpClient retrofit: Retrofit): ChallengeApiService {
        return retrofit.create(ChallengeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageApiService(@AuthOkHttpClient retrofit: Retrofit): ImageApiService {
        return retrofit.create(ImageApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEmojiApiService(@AuthOkHttpClient retrofit: Retrofit): EmojiApiService {
        return retrofit.create(EmojiApiService::class.java)
    }
}
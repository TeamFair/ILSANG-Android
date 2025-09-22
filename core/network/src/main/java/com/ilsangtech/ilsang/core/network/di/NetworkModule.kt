package com.ilsangtech.ilsang.core.network.di

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import com.ilsangtech.ilsang.core.datastore.auth.AuthLocalDataSource
import com.ilsangtech.ilsang.core.network.BuildConfig
import com.ilsangtech.ilsang.core.network.api.AreaApiService
import com.ilsangtech.ilsang.core.network.api.AuthApiService
import com.ilsangtech.ilsang.core.network.api.BannerApiService
import com.ilsangtech.ilsang.core.network.api.ChallengeApiService
import com.ilsangtech.ilsang.core.network.api.CouponApiService
import com.ilsangtech.ilsang.core.network.api.EmojiApiService
import com.ilsangtech.ilsang.core.network.api.ImageApiService
import com.ilsangtech.ilsang.core.network.api.MissionApiService
import com.ilsangtech.ilsang.core.network.api.QuestApiService
import com.ilsangtech.ilsang.core.network.api.QuizApiService
import com.ilsangtech.ilsang.core.network.api.RankApiService
import com.ilsangtech.ilsang.core.network.api.SeasonApiService
import com.ilsangtech.ilsang.core.network.api.TitleApiService
import com.ilsangtech.ilsang.core.network.api.UserApiService
import com.ilsangtech.ilsang.core.network.model.auth.OAuthRefreshRequest
import com.ilsangtech.ilsang.core.network.model.auth.OAuthRefreshResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
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
        @BaseOkHttpClient okHttpClient: OkHttpClient,
        authLocalDataStore: AuthLocalDataSource
    ): Authenticator {
        return object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                if (getResponseCount(response) >= 2) return null
                return runBlocking {
                    try {
                        val (accessToken, refreshToken) = authLocalDataStore.getAuthTokens()
                        val refreshRequestString =
                            Json.encodeToString(OAuthRefreshRequest(accessToken, refreshToken))

                        val refreshResponse = Json.decodeFromString<OAuthRefreshResponse>(
                            okHttpClient.newCall(
                                Request.Builder()
                                    .url(BuildConfig.SERVER_URL + "/api/v1/open/login/refresh")
                                    .post(
                                        refreshRequestString
                                            .toRequestBody("application/json".toMediaType())
                                    )
                                    .build()
                            ).execute().body!!.string()
                        )

                        authLocalDataStore.saveAuthTokens(
                            refreshResponse.accessToken,
                            refreshResponse.refreshToken
                        )

                        response.request.newBuilder()
                            .header("Authorization", "Bearer ${refreshResponse.accessToken}")
                            .build()

                    } catch (_: Exception) {
                        return@runBlocking null
                    }
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
    fun provideAuthInterceptor(
        authLocalDataSource: AuthLocalDataSource,
    ): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val token = runBlocking { authLocalDataSource.getAuthTokens().first }
            val newRequest = original.newBuilder()
                .header("Authorization", "Bearer $token")
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
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(authInterceptor)
            authenticator(authenticator)
        }
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
        return builder.build()
    }

    @BaseOkHttpClient
    @Provides
    @Singleton
    fun provideBaseOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideImageLoaderFactory(@AuthOkHttpClient okHttpClient: OkHttpClient): SingletonImageLoader.Factory {
        return object : SingletonImageLoader.Factory {
            override fun newImageLoader(context: PlatformContext): ImageLoader {
                return ImageLoader.Builder(context)
                    .components {
                        add(
                            OkHttpNetworkFetcherFactory(
                                callFactory = okHttpClient
                            )
                        )
                    }.build()
            }
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        val json = Json { encodeDefaults = true }
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("${BuildConfig.SERVER_URL}/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
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

    @Provides
    @Singleton
    fun provideTitleApiService(retrofit: Retrofit): TitleApiService {
        return retrofit.create(TitleApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAreaApiService(retrofit: Retrofit): AreaApiService {
        return retrofit.create(AreaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSeasonApiService(retrofit: Retrofit): SeasonApiService {
        return retrofit.create(SeasonApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMissionApiService(retrofit: Retrofit): MissionApiService {
        return retrofit.create(MissionApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuizApiService(retrofit: Retrofit): QuizApiService {
        return retrofit.create(QuizApiService::class.java)
    }

    @Provides
    @Singleton
    fun provieCouponApiService(retrofit: Retrofit): CouponApiService {
        return retrofit.create(CouponApiService::class.java)
    }
}
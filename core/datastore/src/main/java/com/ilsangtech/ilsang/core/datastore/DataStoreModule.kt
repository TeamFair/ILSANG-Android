package com.ilsangtech.ilsang.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.ilsangtech.ilsang.core.datastore.auth.AuthLocalDataSource
import com.ilsangtech.ilsang.core.datastore.auth.AuthLocalDataSourceImpl
import com.ilsangtech.ilsang.core.datastore.auth.AuthTokensSerializer
import com.ilsangtech.ilsang.core.datastore.user.UserDataStore
import com.ilsangtech.ilsang.core.datastore.user.UserLocalDataSource
import com.ilsangtech.ilsang.core.datastore.user.UserLocalDataSourceImpl
import com.ilsangtech.ilsang.core.datastore.user.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): UserDataStore {
        return UserDataStore(context)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            produceFile = { context.dataStoreFile("user_preferences.pb") }
        )
    }

    @Provides
    @Singleton
    fun provideAuthTokensDataStore(
        @ApplicationContext context: Context,
        authTokensSerializer: AuthTokensSerializer
    ): DataStore<AuthTokens> {
        return DataStoreFactory.create(
            serializer = authTokensSerializer,
            produceFile = { context.dataStoreFile("auth_tokens.pb") }
        )
    }

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(
        authTokensDataStore: DataStore<AuthTokens>
    ): AuthLocalDataSource {
        return AuthLocalDataSourceImpl(authTokensDataStore)
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(
        userDataStore: DataStore<UserPreferences>
    ): UserLocalDataSource {
        return UserLocalDataSourceImpl(userDataStore)
    }
}
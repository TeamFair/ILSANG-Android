package com.ilsangtech.ilsang.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.userDataStore by preferencesDataStore(
    name = "user_preferences"
)

class UserDataStore(context: Context) {
    private val userDataStore = context.userDataStore

    private val shouldShowOnBoardingKey = booleanPreferencesKey("should_show_on_boarding")
    val shouldShowOnBoarding = userDataStore.data.map { preferences ->
        preferences[shouldShowOnBoardingKey] ?: true
    }

    private val userMyZoneKey = stringPreferencesKey("user_my_zone")
    val userMyZone = userDataStore.data.map { preferences ->
        preferences[userMyZoneKey] ?: "R100"
    }

    private val accessTokenKey = stringPreferencesKey("access_token")
    val accessToken = userDataStore.data.map { preferences ->
        preferences[accessTokenKey]
    }

    private val refreshTokenKey = stringPreferencesKey("refresh_token")
    val refreshToken = userDataStore.data.map { preferences ->
        preferences[refreshTokenKey]
    }

    suspend fun setShouldShowOnBoarding(shouldShow: Boolean) {
        userDataStore.edit { preferences ->
            preferences[shouldShowOnBoardingKey] = shouldShow
        }
    }

    suspend fun setUserMyZone(commericalAreaCode: String) {
        userDataStore.edit { preferences ->
            preferences[userMyZoneKey] = commericalAreaCode
        }
    }

    suspend fun setAccessToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[accessTokenKey] = token
        }
    }

    suspend fun setRefreshToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[refreshTokenKey] = token
        }
    }

}
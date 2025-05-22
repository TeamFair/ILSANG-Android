package com.ilsangtech.ilsang.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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

    suspend fun setShouldShowOnBoarding(shouldShow: Boolean) {
        userDataStore.edit { preferences ->
            preferences[shouldShowOnBoardingKey] = shouldShow
        }
    }
}
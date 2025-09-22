package com.ilsangtech.ilsang.core.datastore.auth

interface AuthLocalDataSource {
    suspend fun getAuthTokens(): Pair<String, String>

    suspend fun saveAuthTokens(accessToken: String, refreshToken: String?)
}
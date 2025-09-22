package com.ilsangtech.ilsang.core.data.auth.datasource

import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginResponse

interface AuthRemoteDataSource {
    suspend fun login(idToken: String): OAuthLoginResponse

    suspend fun logout()

    suspend fun withdraw()
}
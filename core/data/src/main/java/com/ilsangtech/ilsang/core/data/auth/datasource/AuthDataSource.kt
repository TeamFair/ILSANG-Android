package com.ilsangtech.ilsang.core.data.auth.datasource

import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginResponse

interface AuthDataSource {
    suspend fun login(idToken: String): OAuthLoginResponse
}
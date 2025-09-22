package com.ilsangtech.ilsang.core.datastore.auth

import androidx.datastore.core.DataStore
import com.ilsangtech.ilsang.core.datastore.AuthTokens
import com.ilsangtech.ilsang.core.datastore.copy
import kotlinx.coroutines.flow.first

class AuthLocalDataSourceImpl(
    private val authDataStore: DataStore<AuthTokens>
) : AuthLocalDataSource {
    override suspend fun getAuthTokens(): Pair<String, String> {
        val authTokens = authDataStore.data.first()
        return authTokens.accessToken to authTokens.refreshToken
    }

    override suspend fun saveAuthTokens(accessToken: String, refreshToken: String?) {
        authDataStore.updateData {
            it.copy {
                this.accessToken = accessToken
                refreshToken?.let { token ->
                    this.refreshToken = token
                }
            }
        }
    }
}
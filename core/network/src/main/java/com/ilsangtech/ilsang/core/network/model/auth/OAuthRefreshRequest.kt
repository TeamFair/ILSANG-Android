package com.ilsangtech.ilsang.core.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthRefreshRequest(
    val accessToken: String,
    val refreshToken: String
)
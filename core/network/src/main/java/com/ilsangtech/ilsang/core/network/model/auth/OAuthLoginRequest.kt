package com.ilsangtech.ilsang.core.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthLoginRequest(
    val provider: String = "GOOGLE",
    val osType: String = "AOS",
    val idToken: String,
    val pushToken: String,
    val deviceUuid: String
)
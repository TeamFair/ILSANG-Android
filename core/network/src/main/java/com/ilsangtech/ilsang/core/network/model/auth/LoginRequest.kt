package com.ilsangtech.ilsang.core.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val accessToken: String,
    val channel: String,
    val email: String,
    val refreshToken: String,
    val userType: String
)
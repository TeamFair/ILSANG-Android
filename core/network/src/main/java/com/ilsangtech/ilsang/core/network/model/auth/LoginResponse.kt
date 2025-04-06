package com.ilsangtech.ilsang.core.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val `data`: AuthData,
    val message: String,
    val status: String
)
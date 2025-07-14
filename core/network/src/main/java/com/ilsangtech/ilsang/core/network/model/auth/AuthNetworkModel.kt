package com.ilsangtech.ilsang.core.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthNetworkModel(
    val authorization: String,
    val refreshToken: String?,
)
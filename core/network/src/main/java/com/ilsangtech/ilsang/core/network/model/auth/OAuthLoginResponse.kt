package com.ilsangtech.ilsang.core.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthLoginResponse(
    val data: AuthNetworkModel,
    val message: String,
    val status: String
)
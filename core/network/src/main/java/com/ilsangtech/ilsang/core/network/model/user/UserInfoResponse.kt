package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("data") val userInfoNetworkModel: UserInfoNetworkModel,
    val message: String,
    val status: String
)
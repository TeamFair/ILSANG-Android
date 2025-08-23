package com.ilsangtech.ilsang.core.network.model.user

import com.ilsangtech.ilsang.core.network.model.title.TitleNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val id: String,
    val channel: String,
    val commercialAreaCode: String?,
    val email: String,
    val nickname: String,
    val profileImageId: String?,
    val status: String,
    val statusUpdatedAt: String,
    val title: TitleNetworkModel?
)
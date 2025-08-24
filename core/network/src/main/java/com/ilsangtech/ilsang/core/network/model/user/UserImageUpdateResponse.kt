package com.ilsangtech.ilsang.core.network.model.user

import com.ilsangtech.ilsang.core.network.model.title.TitleNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class UserImageUpdateResponse(
    val channel: String,
    val commercialAreaCode: String,
    val email: String,
    val id: String,
    val nickname: String,
    val profileImageId: String,
    val status: String,
    val statusUpdatedAt: String,
    val title: TitleNetworkModel
)

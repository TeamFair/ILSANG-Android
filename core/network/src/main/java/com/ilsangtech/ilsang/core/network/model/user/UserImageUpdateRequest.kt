package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserImageUpdateRequest(
    val imageId: String
)
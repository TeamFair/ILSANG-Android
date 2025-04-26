package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class NicknameUpdateRequest(
    val nickname: String
)

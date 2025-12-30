package com.ilsangtech.ilsang.core.network.model.common

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String?,
    val status: Int?,
    val error: String?
)

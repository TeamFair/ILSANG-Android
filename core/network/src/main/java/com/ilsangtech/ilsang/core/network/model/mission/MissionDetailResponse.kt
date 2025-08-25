package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class MissionDetailResponse(
    val id: Int,
    val questId: Int,
    val createdAt: String,
    val createdBy: String,
    val sortOrder: Int,
    val title: String,
    val type: String,
    val updatedAt: String,
    val updatedBy: String
)
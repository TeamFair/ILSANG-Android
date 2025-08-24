package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserPointResponse(
    val commercialAreaPoint: Int,
    val completedQuestCount: Int,
    val contributionPoint: Int,
    val metroAreaPoint: Int
)
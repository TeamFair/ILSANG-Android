package com.ilsangtech.ilsang.core.model.title

data class LegendTitle(
    val rank: Int,
    val nickname: String,
    val profileImageId: String?,
    val userPoint: Int,
    val title: Title,
    val createdAt: String
)

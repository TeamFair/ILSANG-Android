package com.ilsangtech.ilsang.core.model

data class Reward(
    val content: String,
    val discountRate: Int,
    val quantity: Int,
    val questId: String,
    val rewardId: String,
    val target: String,
    val title: String,
    val type: String
)

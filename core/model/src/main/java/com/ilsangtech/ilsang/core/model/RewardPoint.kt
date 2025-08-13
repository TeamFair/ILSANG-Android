package com.ilsangtech.ilsang.core.model

sealed class RewardPoint(open val point: Int) {
    data class Metro(override val point: Int) : RewardPoint(point)
    data class Commerical(override val point: Int) : RewardPoint(point)
    data class Contribute(override val point: Int) : RewardPoint(point)
}
package com.ilsangtech.ilsang.core.model.reward

sealed class RewardPoint(open val point: Int) {
    data class Metro(override val point: Int) : RewardPoint(point)
    data class Commercial(override val point: Int) : RewardPoint(point)
    data class Contribute(override val point: Int) : RewardPoint(point)
}
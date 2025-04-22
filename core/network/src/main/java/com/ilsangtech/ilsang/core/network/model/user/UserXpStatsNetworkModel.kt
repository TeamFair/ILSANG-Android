package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserXpStatsNetworkModel(
    @SerialName("charm_stat") val charmStat: Int,
    @SerialName("fun_stat") val funStat: Int,
    @SerialName("intellect_stat") val intellectStat: Int,
    @SerialName("sociability_stat") val sociabilityStat: Int,
    @SerialName("strength_stat") val strengthStat: Int
)
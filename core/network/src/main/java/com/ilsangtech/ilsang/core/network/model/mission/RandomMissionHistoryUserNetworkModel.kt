package com.ilsangtech.ilsang.core.network.model.mission

import com.ilsangtech.ilsang.core.network.model.title.TitleNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class RandomMissionHistoryUserNetworkModel(
    val nickname: String,
    val profileImageId: String,
    val title: TitleNetworkModel,
    val userId: String
)
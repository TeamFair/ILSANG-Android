package com.ilsangtech.ilsang.core.network.model.rank

import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class UserRankNetworkModel(
    val userId: String,
    val nickName: String,
    val point: Int?,
    val profileImageId: String?,
    val rank: Int?,
    val title: UserTitleNetworkModel?
)
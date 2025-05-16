package com.ilsangtech.ilsang.core.network.model.rank

import com.ilsangtech.ilsang.core.network.model.title.TitleNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class UserRankNetworkModel(
    val customerId: String,
    val lank: Int,
    val nickname: String,
    val profileImage: String?,
    val xpSum: Int,
    val title: TitleNetworkModel?
)
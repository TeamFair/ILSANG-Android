package com.ilsangtech.ilsang.core.network.model.rank

import com.ilsangtech.ilsang.core.network.model.title.TitleNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class XpTypeRankNetworkModel(
    val customerId: String,
    val nickname: String,
    val profileImage: String?,
    val xpPoint: Int,
    val xpType: String,
    val title: TitleNetworkModel? = null,
    val xpTotalPoint: Int? = null
)
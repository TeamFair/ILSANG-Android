package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class TitleData(
    val title: TitleNetworkModel,
    val titleHistory: TitleHistoryNetworkModel?
)
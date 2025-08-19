package com.ilsangtech.ilsang.core.network.model.area

import kotlinx.serialization.Serializable

@Serializable
data class AreaListResponse(
    val metroAreaList: List<MetroAreaNetworkModel>
)
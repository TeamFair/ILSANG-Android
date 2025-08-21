package com.ilsangtech.ilsang.core.data.area.datasource

import com.ilsangtech.ilsang.core.network.model.area.MetroAreaNetworkModel

interface AreaDataSource {
    suspend fun getMetroAreaList(): List<MetroAreaNetworkModel>

    suspend fun getMetroArea(metroAreaCode: String): MetroAreaNetworkModel
}
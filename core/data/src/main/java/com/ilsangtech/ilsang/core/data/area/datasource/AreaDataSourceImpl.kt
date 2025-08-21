package com.ilsangtech.ilsang.core.data.area.datasource

import com.ilsangtech.ilsang.core.network.api.AreaApiService
import com.ilsangtech.ilsang.core.network.model.area.MetroAreaNetworkModel

class AreaDataSourceImpl(
    private val areaApiService: AreaApiService
) : AreaDataSource {
    override suspend fun getMetroAreaList(): List<MetroAreaNetworkModel> {
        return areaApiService.getMetroAreaList()
    }
}
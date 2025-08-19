package com.ilsangtech.ilsang.core.data.area.datasource

import com.ilsangtech.ilsang.core.network.model.area.AreaListResponse

interface AreaDataSource {
    suspend fun getAreaList(): AreaListResponse
}
package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.area.AreaListResponse
import retrofit2.http.GET

interface AreaApiService {
    @GET("api/v1/area")
    suspend fun getAreaList(): AreaListResponse
}
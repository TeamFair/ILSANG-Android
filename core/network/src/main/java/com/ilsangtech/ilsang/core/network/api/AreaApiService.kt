package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.area.MetroAreaNetworkModel
import retrofit2.http.GET

interface AreaApiService {
    @GET("api/v1/area/metro")
    suspend fun getMetroAreaList(): List<MetroAreaNetworkModel>
}
package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.area.CommercialAreaNetworkModel
import com.ilsangtech.ilsang.core.network.model.area.MetroAreaNetworkModel
import retrofit2.http.GET
import retrofit2.http.Path

interface AreaApiService {
    @GET("api/v1/area/metro")
    suspend fun getMetroAreaList(): List<MetroAreaNetworkModel>

    @GET("api/v1/area/metro/{metroAreaCode}")
    suspend fun getMetroAreaDetail(
        @Path("metroAreaCode") metroAreaCode: String
    ): MetroAreaNetworkModel

    @GET("api/v1/area/commercial/{commercialAreaCode}")
    suspend fun getCommercialAreaDetail(
        @Path("commercialAreaCode") commercialAreaCode: String
    ): CommercialAreaNetworkModel
}
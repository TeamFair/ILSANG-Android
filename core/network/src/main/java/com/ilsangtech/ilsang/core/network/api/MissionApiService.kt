package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.mission.MissionDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MissionApiService {
    @GET("api/v1/mission/{id}")
    suspend fun getMissionById(
        @Path("id") id: Int
    ): MissionDetailResponse
}
package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.season.SeasonNetworkModel
import retrofit2.http.GET

interface SeasonApiService {
    @GET("api/v1/season")
    fun getSeasonList(): List<SeasonNetworkModel>
}
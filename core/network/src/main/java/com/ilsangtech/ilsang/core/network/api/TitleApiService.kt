package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.title.TitleListResponse
import retrofit2.http.GET

interface TitleApiService {
    @GET("customer/title/history")
    suspend fun getTitleList(): TitleListResponse
}
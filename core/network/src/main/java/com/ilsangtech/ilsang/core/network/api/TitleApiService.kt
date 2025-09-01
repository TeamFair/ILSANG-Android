package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel
import retrofit2.http.GET

interface TitleApiService {
    @GET("api/v1/title")
    suspend fun getTitleList(): List<TitleDetailNetworkModel>

    @GET("api/v1/user/title")
    suspend fun getUserTitleList(): List<UserTitleNetworkModel>
}
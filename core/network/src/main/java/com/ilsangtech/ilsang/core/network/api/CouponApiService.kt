package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.coupon.CouponDetailNetworkModel
import retrofit2.http.GET

interface CouponApiService {
    @GET("api/v1/user/coupon")
    suspend fun getCouponList(page: Int, size: Int): List<CouponDetailNetworkModel>
}
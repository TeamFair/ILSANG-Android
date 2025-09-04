package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.coupon.CouponDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.coupon.CouponPasswordVerifyRequest
import com.ilsangtech.ilsang.core.network.model.coupon.CouponPasswordVerifyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CouponApiService {
    @GET("api/v1/user/coupon")
    suspend fun getCouponList(page: Int, size: Int): List<CouponDetailNetworkModel>

    @POST("api/v1/user/coupon/{id}/verify-password")
    suspend fun verifyPassword(
        @Path("id") id: Int,
        @Body password: CouponPasswordVerifyRequest
    ): CouponPasswordVerifyResponse
}
package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.coupon.CouponDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.coupon.CouponPasswordVerifyRequest
import com.ilsangtech.ilsang.core.network.model.coupon.CouponPasswordVerifyResponse
import com.ilsangtech.ilsang.core.network.model.coupon.CouponUpdateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CouponApiService {
    @GET("api/v1/user/coupon")
    suspend fun getCouponList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<CouponDetailNetworkModel>

    @POST("api/v1/user/coupon/{id}/verify-password")
    suspend fun verifyPassword(
        @Path("id") id: Int,
        @Body password: CouponPasswordVerifyRequest
    ): CouponPasswordVerifyResponse

    @PUT("api/v1/user/coupon/{id}")
    suspend fun updateCoupon(
        @Path("id") id: Int,
        @Body updateRequest: CouponUpdateRequest
    ): CouponDetailNetworkModel
}
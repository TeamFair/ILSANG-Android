package com.ilsangtech.ilsang.core.data.coupon.datasource

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.network.model.coupon.CouponDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.coupon.CouponPasswordVerifyResponse
import kotlinx.coroutines.flow.Flow

interface CouponDataSource {
    fun getCouponList(): Flow<PagingData<CouponDetailNetworkModel>>

    suspend fun verifyCouponPassword(id: Int, password: String): CouponPasswordVerifyResponse

    suspend fun updateCoupon(
        id: Int,
        couponUseYn: Boolean,
        couponExpireYn: Boolean
    ): CouponDetailNetworkModel
}
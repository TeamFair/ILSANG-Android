package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.model.coupon.Coupon
import kotlinx.coroutines.flow.Flow

interface CouponRepository {
    fun getCouponList(): Flow<PagingData<Coupon>>

    suspend fun verifyCouponPassword(couponId: Int, password: String): Result<Boolean>

    suspend fun useCoupon(couponId: Int): Result<Unit>
}
package com.ilsangtech.ilsang.core.data.coupon.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.data.coupon.CouponPagingSource
import com.ilsangtech.ilsang.core.network.api.CouponApiService
import com.ilsangtech.ilsang.core.network.model.coupon.CouponDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.coupon.CouponPasswordVerifyRequest
import com.ilsangtech.ilsang.core.network.model.coupon.CouponPasswordVerifyResponse
import com.ilsangtech.ilsang.core.network.model.coupon.CouponUpdateRequest
import kotlinx.coroutines.flow.Flow

class CouponDataSourceImpl(
    private val couponApiService: CouponApiService
) : CouponDataSource {
    override fun getCouponList(): Flow<PagingData<CouponDetailNetworkModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CouponPagingSource(couponApiService) }
        ).flow
    }

    override suspend fun verifyCouponPassword(
        id: Int,
        password: String
    ): CouponPasswordVerifyResponse {
        return couponApiService.verifyPassword(
            id = id,
            password = CouponPasswordVerifyRequest(password = password)
        )
    }

    override suspend fun updateCoupon(
        id: Int,
        couponUseYn: Boolean,
        couponExpireYn: Boolean
    ): CouponDetailNetworkModel {
        return couponApiService.updateCoupon(
            id = id,
            updateRequest = CouponUpdateRequest(
                couponUseYn = couponUseYn,
                couponExpireYn = couponExpireYn
            )
        )
    }
}
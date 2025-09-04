package com.ilsangtech.ilsang.core.data.coupon.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ilsangtech.ilsang.core.data.coupon.datasource.CouponDataSource
import com.ilsangtech.ilsang.core.data.coupon.mapper.toCoupon
import com.ilsangtech.ilsang.core.domain.CouponRepository
import com.ilsangtech.ilsang.core.model.coupon.Coupon
import com.ilsangtech.ilsang.core.network.model.coupon.CouponDetailNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CouponRepositoryImpl(
    private val couponDataSource: CouponDataSource
) : CouponRepository {
    override fun getCouponList(): Flow<PagingData<Coupon>> {
        return couponDataSource.getCouponList().map { pagingData ->
            pagingData.map(CouponDetailNetworkModel::toCoupon)
        }
    }

    override suspend fun verifyCouponPassword(
        couponId: Int,
        password: String
    ): Result<Boolean> {
        return runCatching {
            couponDataSource.verifyCouponPassword(couponId, password).success
        }
    }

    override suspend fun useCoupon(couponId: Int): Result<Unit> {
        return runCatching {
            couponDataSource.updateCoupon(
                id = couponId,
                couponUseYn = true,
                couponExpireYn = false
            )
        }
    }
}
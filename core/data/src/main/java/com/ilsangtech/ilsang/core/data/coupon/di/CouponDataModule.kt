package com.ilsangtech.ilsang.core.data.coupon.di

import com.ilsangtech.ilsang.core.data.coupon.datasource.CouponDataSource
import com.ilsangtech.ilsang.core.data.coupon.datasource.CouponDataSourceImpl
import com.ilsangtech.ilsang.core.data.coupon.repository.CouponRepositoryImpl
import com.ilsangtech.ilsang.core.domain.CouponRepository
import com.ilsangtech.ilsang.core.network.api.CouponApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CouponDataModule {
    @Provides
    @Singleton
    fun provideCouponDataSource(couponApiService: CouponApiService): CouponDataSource {
        return CouponDataSourceImpl(couponApiService)
    }

    @Provides
    @Singleton
    fun provideCouponRepository(couponDataSource: CouponDataSource): CouponRepository {
        return CouponRepositoryImpl(couponDataSource)
    }
}
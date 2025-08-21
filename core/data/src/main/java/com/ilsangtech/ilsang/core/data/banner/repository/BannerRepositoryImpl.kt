package com.ilsangtech.ilsang.core.data.banner.repository

import com.ilsangtech.ilsang.core.data.banner.datasource.BannerDataSource
import com.ilsangtech.ilsang.core.data.banner.mapper.toBanner
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.model.banner.Banner
import com.ilsangtech.ilsang.core.network.model.banner.BannerNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val bannerDataSource: BannerDataSource,
) : BannerRepository {
    override suspend fun getBanners(): Flow<List<Banner>> = flow {
        emit(bannerDataSource.getBanners().content.map(BannerNetworkModel::toBanner))
    }
}
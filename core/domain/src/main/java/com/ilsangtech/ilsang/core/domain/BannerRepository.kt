package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.banner.Banner
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    suspend fun getBanners(): Flow<List<Banner>>
}
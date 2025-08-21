package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.banner.Banner

interface BannerRepository {
    suspend fun getBanners(): List<Banner>
}
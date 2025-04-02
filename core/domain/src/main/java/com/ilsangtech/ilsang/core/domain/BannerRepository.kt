package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.Banner

interface BannerRepository {
    suspend fun getBanners(): List<Banner>
}
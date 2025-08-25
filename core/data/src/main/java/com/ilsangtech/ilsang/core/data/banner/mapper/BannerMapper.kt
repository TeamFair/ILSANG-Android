package com.ilsangtech.ilsang.core.data.banner.mapper

import com.ilsangtech.ilsang.core.model.banner.Banner
import com.ilsangtech.ilsang.core.network.model.banner.BannerNetworkModel

internal fun BannerNetworkModel.toBanner(): Banner {
    return Banner(
        id = id,
        bannerImageId = bannerImageId,
        description = description,
        navigationTitle = navigationTitle,
        title = title
    )
}
package com.ilsangtech.ilsang.core.model.banner

data class Banner(
    val id: Int,
    val bannerImageId: String,
    val description: String,
    val navigationTitle: String,
    val title: String,
    val useYn: Boolean
)
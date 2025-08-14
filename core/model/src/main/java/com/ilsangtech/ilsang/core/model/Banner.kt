package com.ilsangtech.ilsang.core.model

data class Banner(
    val id: Int,
    val title: String,
    val imageId: String,
    val imageUrl: String,
    val description: String,
    val activeYn: String
)
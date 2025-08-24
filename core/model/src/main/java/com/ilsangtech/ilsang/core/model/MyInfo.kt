package com.ilsangtech.ilsang.core.model

import com.ilsangtech.ilsang.core.model.title.Title

data class MyInfo(
    val id: String,
    val channel: String,
    val isCommercialAreaCode: String?,
    val myCommericalAreaCode: String,
    val email: String,
    val nickname: String,
    val profileImageId: String?,
    val status: String,
    val statusUpdatedAt: String,
    val title: Title?
)

package com.ilsangtech.ilsang.core.model.user

import com.ilsangtech.ilsang.core.model.title.UserTitle

data class MyInfo(
    val id: String,
    val channel: String,
    val totalPoint: Int,
    val isCommercialAreaCode: String?,
    val myCommericalAreaCode: String,
    val email: String,
    val nickname: String,
    val profileImageId: String?,
    val status: String,
    val statusUpdatedAt: String,
    val title: UserTitle?,
    val showIsZoneDialogAgain: Boolean,
    val shouldShowSeasonOpenDialog: Boolean
)
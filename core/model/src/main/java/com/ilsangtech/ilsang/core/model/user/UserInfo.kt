package com.ilsangtech.ilsang.core.model.user

import com.ilsangtech.ilsang.core.model.title.UserTitle

data class UserInfo(
    val id: String,
    val channel: String,
    val commercialAreaCode: String?,
    val email: String,
    val nickname: String,
    val profileImageId: String?,
    val status: String,
    val statusUpdatedAt: String,
    val userTitle: UserTitle?
)
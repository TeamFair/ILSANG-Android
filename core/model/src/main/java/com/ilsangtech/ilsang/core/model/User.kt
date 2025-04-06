package com.ilsangtech.ilsang.core.model

data class User(
    val accessToken: String,
    val authorization: String? = null,
    val nickname: String? = null,
    val email: String? = null,
    val profileImage: String? = null,
)

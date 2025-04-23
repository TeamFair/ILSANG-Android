package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats

interface UserRepository {
    var currentUser: UserInfo?

    suspend fun login(email: String, accessToken: String)

    suspend fun updateUserInfo()

    suspend fun getUserXpStats(customerId: String? = null): UserXpStats

    suspend fun updateUserNickname(nickname: String)
}
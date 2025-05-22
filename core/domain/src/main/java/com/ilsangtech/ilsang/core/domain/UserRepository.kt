package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    var currentUser: UserInfo?

    val shouldShowOnBoarding: Flow<Boolean>

    suspend fun login(email: String, accessToken: String)

    suspend fun updateUserInfo()

    suspend fun getUserXpStats(customerId: String? = null): UserXpStats

    suspend fun updateUserNickname(nickname: String)

    suspend fun completeOnBoarding()
}
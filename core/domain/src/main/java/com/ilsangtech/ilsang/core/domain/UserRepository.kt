package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    var currentUser: MyInfo?

    val shouldShowOnBoarding: Flow<Boolean>

    suspend fun login(email: String, accessToken: String)

    suspend fun logout(): Result<Unit>

    suspend fun withdraw(): Result<Unit>

    suspend fun updateMyInfo()

    suspend fun getUserInfo(userId: String): Result<UserInfo>

    suspend fun getUserXpStats(customerId: String? = null): UserXpStats

    suspend fun updateUserNickname(nickname: String)

    suspend fun updateUserImage(imageData: ByteArray): Result<Unit>

    suspend fun deleteUserImage(): Result<Unit>

    suspend fun completeOnBoarding()
}
package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val shouldShowOnBoarding: Flow<Boolean>

    suspend fun login(idToken: String)

    suspend fun logout(): Result<Unit>

    suspend fun withdraw(): Result<Unit>

    fun getMyInfo(): Flow<MyInfo>

    suspend fun getUserInfo(userId: String): Result<UserInfo>

    suspend fun getUserXpStats(customerId: String? = null): UserXpStats

    suspend fun updateUserNickname(nickname: String)

    suspend fun updateUserImage(imageData: ByteArray): Result<Unit>

    suspend fun deleteUserImage(): Result<Unit>

    suspend fun completeOnBoarding()

    suspend fun updateUserTitle(titleHistoryId: String): Result<Unit>

    suspend fun updateUserMyZone(commericalAreaCode: String): Result<Unit>

    suspend fun updateUserIsZone(commericalAreaCode: String): Result<Unit>
}
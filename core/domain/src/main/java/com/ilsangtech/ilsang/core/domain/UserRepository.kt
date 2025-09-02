package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.core.model.user.UserCommercialPoint
import com.ilsangtech.ilsang.core.model.user.UserPoint
import com.ilsangtech.ilsang.core.model.user.UserPointSummary
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val shouldShowOnBoarding: Flow<Boolean>

    fun getMyInfo(): Flow<MyInfo>

    fun getUserInfo(userId: String): Flow<UserInfo>

    fun getUserPoint(userId: String? = null, seasonId: Int? = null): Flow<UserPoint>

    fun getUserPointSummary(seasonId: Int): Flow<UserPointSummary>

    fun getUserCommercialPoint(userId: String? = null): Flow<UserCommercialPoint>

    suspend fun getUserXpStats(customerId: String? = null): UserXpStats

    suspend fun updateUserNickname(nickname: String)

    suspend fun updateUserImage(profileImageId: String?): Result<Unit>

    suspend fun deleteUserImage(): Result<Unit>

    suspend fun completeOnBoarding()

    suspend fun updateUserTitle(titleHistoryId: Int): Result<Unit>

    suspend fun updateUserMyZone(commericalAreaCode: String): Result<Unit>

    suspend fun updateUserIsZone(commericalAreaCode: String): Result<Unit>

    suspend fun updateShowIsZoneDialogAgain(showAgain: Boolean)
}
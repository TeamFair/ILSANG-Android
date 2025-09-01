package com.ilsangtech.ilsang.core.data.user.repository

import com.ilsangtech.ilsang.core.data.user.datasource.UserDataSource
import com.ilsangtech.ilsang.core.data.user.mapper.toMyInfo
import com.ilsangtech.ilsang.core.data.user.mapper.toUserCommercialPoint
import com.ilsangtech.ilsang.core.data.user.mapper.toUserInfo
import com.ilsangtech.ilsang.core.data.user.mapper.toUserPoint
import com.ilsangtech.ilsang.core.data.user.mapper.toUserPointSummary
import com.ilsangtech.ilsang.core.data.user.toUserXpStats
import com.ilsangtech.ilsang.core.datastore.UserDataStore
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.core.model.user.UserCommercialPoint
import com.ilsangtech.ilsang.core.model.user.UserPoint
import com.ilsangtech.ilsang.core.model.user.UserPointSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userDataStore: UserDataStore
) : UserRepository {
    override val shouldShowOnBoarding: Flow<Boolean> = userDataStore.shouldShowOnBoarding

    override fun getMyInfo(): Flow<MyInfo> =
        combine(userDataStore.userMyZone, getUserPoint()) { myZoneCommercialAreaCode, userPoint ->
            val userInfoResponse = userDataSource.getUserInfo(userId = null)
            val totalPoint =
                userPoint.metroAreaPoint + userPoint.commercialAreaPoint + userPoint.contributionPoint
            userInfoResponse.toMyInfo(
                totalPoint = totalPoint,
                myZoneCommercialAreaCode = myZoneCommercialAreaCode
            )
        }

    override fun getUserInfo(userId: String): Flow<UserInfo> = flow {
        emit(
            userDataSource.getUserInfo(userId = userId).toUserInfo()
        )
    }

    override fun getUserPoint(userId: String?, seasonId: Int?): Flow<UserPoint> = flow {
        emit(
            userDataSource.getUserPoint(
                userId = userId,
                seasonId = seasonId
            ).toUserPoint()
        )
    }

    override fun getUserPointSummary(seasonId: Int): Flow<UserPointSummary> = flow {
        emit(
            userDataSource.getUserPointSummary(
                seasonId = seasonId
            ).toUserPointSummary()
        )
    }

    override fun getUserCommercialPoint(userId: String?): Flow<UserCommercialPoint> = flow {
        emit(
            userDataSource.getUserCommercialPoint(
                userId = userId
            ).toUserCommercialPoint()
        )
    }

    override suspend fun getUserXpStats(customerId: String?): UserXpStats {
        return userDataSource.getUserXpStats(
            customerId = customerId
        ).userXpStatsNetworkModel.toUserXpStats()
    }

    override suspend fun updateUserNickname(nickname: String) {
        userDataSource.updateUserNickname(nickname = nickname)
    }

    override suspend fun updateUserImage(profileImageId: String?): Result<Unit> {
        return runCatching {
            userDataSource.updateUserImage(profileImageId)
        }
    }

    override suspend fun deleteUserImage(): Result<Unit> {
        return runCatching {
            userDataSource.deleteUserImage()
        }
    }

    override suspend fun completeOnBoarding() {
        userDataStore.setShouldShowOnBoarding(false)
    }

    override suspend fun updateUserTitle(titleHistoryId: String): Result<Unit> {
        return runCatching {
            userDataSource.updateUserTitle(titleHistoryId)
        }
    }

    override suspend fun updateUserMyZone(commericalAreaCode: String): Result<Unit> {
        return runCatching {
            userDataStore.setUserMyZone(commericalAreaCode)
        }
    }

    override suspend fun updateUserIsZone(commericalAreaCode: String): Result<Unit> {
        return runCatching {
            userDataSource.updateUserIsZone(commericalAreaCode)
        }
    }
}
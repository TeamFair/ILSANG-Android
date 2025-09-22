package com.ilsangtech.ilsang.core.data.user.repository

import com.ilsangtech.ilsang.core.data.user.datasource.UserRemoteDataSource
import com.ilsangtech.ilsang.core.data.user.mapper.toMyInfo
import com.ilsangtech.ilsang.core.data.user.mapper.toUserCommercialPoint
import com.ilsangtech.ilsang.core.data.user.mapper.toUserInfo
import com.ilsangtech.ilsang.core.data.user.mapper.toUserPoint
import com.ilsangtech.ilsang.core.data.user.mapper.toUserPointSummary
import com.ilsangtech.ilsang.core.data.user.toUserXpStats
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.core.model.user.UserCommercialPoint
import com.ilsangtech.ilsang.core.model.user.UserPoint
import com.ilsangtech.ilsang.core.model.user.UserPointSummary
import com.ilsangtech.ilsang.core.util.DateConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override val shouldShowOnBoarding: Flow<Boolean> = userDataStore.shouldShowOnBoarding

    override fun getMyInfo(): Flow<MyInfo> =
        combine(
            userDataStore.userMyZone,
            userDataStore.showIsZoneDialogAgain,
            userDataStore.shouldShowSeasonOpenDialog,
            getUserPoint(),
            getUserInfo(null)
        ) { myZoneCommercialAreaCode, showIsZoneDialogAgain, shouldShowSeasonOpenDialog, userPoint, userInfo ->
            val totalPoint =
                userPoint.metroAreaPoint + userPoint.commercialAreaPoint + userPoint.contributionPoint
            userInfo.toMyInfo(
                totalPoint = totalPoint,
                myZoneCommercialAreaCode = myZoneCommercialAreaCode,
                showIsZoneDialogAgain = userInfo.commercialAreaCode == null && showIsZoneDialogAgain,
                shouldShowSeasonOpenDialog = shouldShowSeasonOpenDialog
            )
        }

    override fun getUserInfo(userId: String?): Flow<UserInfo> = flow {
        emit(
            userRemoteDataSource.getUserInfo(userId = userId).toUserInfo()
        )
    }

    override fun getUserPoint(userId: String?, seasonId: Int?): Flow<UserPoint> = flow {
        emit(
            userRemoteDataSource.getUserPoint(
                userId = userId,
                seasonId = seasonId
            ).toUserPoint()
        )
    }

    override fun getUserPointSummary(seasonId: Int): Flow<UserPointSummary> = flow {
        emit(
            userRemoteDataSource.getUserPointSummary(
                seasonId = seasonId
            ).toUserPointSummary()
        )
    }

    override fun getUserCommercialPoint(userId: String?): Flow<UserCommercialPoint> = flow {
        emit(
            userRemoteDataSource.getUserCommercialPoint(
                userId = userId
            ).toUserCommercialPoint()
        )
    }

    override suspend fun getUserXpStats(customerId: String?): UserXpStats {
        return userRemoteDataSource.getUserXpStats(
            customerId = customerId
        ).userXpStatsNetworkModel.toUserXpStats()
    }

    override suspend fun updateUserNickname(nickname: String) {
        userRemoteDataSource.updateUserNickname(nickname = nickname)
    }

    override suspend fun updateUserImage(profileImageId: String?): Result<Unit> {
        return runCatching {
            userRemoteDataSource.updateUserImage(profileImageId)
        }
    }

    override suspend fun deleteUserImage(): Result<Unit> {
        return runCatching {
            userRemoteDataSource.deleteUserImage()
        }
    }

    override suspend fun completeOnBoarding() {
        userDataStore.setShouldShowOnBoarding(false)
    }

    override suspend fun updateUserTitle(titleHistoryId: Int?): Result<Unit> {
        return runCatching {
            userRemoteDataSource.updateUserTitle(titleHistoryId)
        }
    }

    override suspend fun updateUserMyZone(commericalAreaCode: String): Result<Unit> {
        return runCatching {
            userDataStore.setUserMyZone(commericalAreaCode)
        }
    }

    override suspend fun updateUserIsZone(commericalAreaCode: String): Result<Unit> {
        return runCatching {
            userRemoteDataSource.updateUserIsZone(commericalAreaCode)
        }
    }

    override suspend fun updateShowIsZoneDialogAgain(showAgain: Boolean) {
        return userDataStore.setShowIsZoneDialogAgain(showAgain)
    }

    override suspend fun updateShouldShowSeasonOpenDialog(shouldShow: Boolean) {
        return userDataStore.setShouldShowSeasonOpenDialog(shouldShow)
    }
}
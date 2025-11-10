package com.ilsangtech.ilsang.core.data.user.repository

import com.ilsangtech.ilsang.core.data.user.datasource.UserRemoteDataSource
import com.ilsangtech.ilsang.core.data.user.mapper.toMyInfo
import com.ilsangtech.ilsang.core.data.user.mapper.toUserCommercialPoint
import com.ilsangtech.ilsang.core.data.user.mapper.toUserInfo
import com.ilsangtech.ilsang.core.data.user.mapper.toUserPoint
import com.ilsangtech.ilsang.core.data.user.mapper.toUserPointSummary
import com.ilsangtech.ilsang.core.datastore.user.UserLocalDataSource
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.user.MyInfo
import com.ilsangtech.ilsang.core.model.user.UserCommercialPoint
import com.ilsangtech.ilsang.core.model.user.UserInfo
import com.ilsangtech.ilsang.core.model.user.UserPoint
import com.ilsangtech.ilsang.core.model.user.UserPointSummary
import com.ilsangtech.ilsang.core.util.DateConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override val shouldShowOnBoarding: Flow<Boolean> =
        userLocalDataSource.getUserPreferences().map { !it.isOnBoardingCompleted }

    override fun getMyInfo(): Flow<MyInfo> =
        combine(
            userLocalDataSource.getUserPreferences(),
            getUserPoint(),
            getUserInfo(null)
        ) { userPreferences, userPoint, userInfo ->
            val totalPoint =
                userPoint.metroAreaPoint + userPoint.commercialAreaPoint + userPoint.contributionPoint
            userInfo.toMyInfo(
                totalPoint = totalPoint,
                myZoneCommercialAreaCode = if (userPreferences.userMyZone.isNullOrBlank()) {
                    "R100"
                } else {
                    userPreferences.userMyZone
                },
                showIsZoneDialogAgain = userInfo.commercialAreaCode == null
                        && DateConverter.isAfterDays(userPreferences.isZoneDialogRejectDate),
                shouldShowSeasonOpenDialog = !userPreferences.isSeasonOpenDialogRejected
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
        userLocalDataSource.updateOnBoardingCompleted(true)
    }

    override suspend fun updateUserTitle(titleHistoryId: Int?): Result<Unit> {
        return runCatching {
            userRemoteDataSource.updateUserTitle(titleHistoryId)
        }
    }

    override suspend fun updateUserMyZone(commericalAreaCode: String): Result<Unit> {
        return runCatching {
            userLocalDataSource.updateUserMyZone(commericalAreaCode)
        }
    }

    override suspend fun updateUserIsZone(commericalAreaCode: String): Result<Unit> {
        return runCatching {
            userRemoteDataSource.updateUserIsZone(commericalAreaCode)
        }
    }

    override suspend fun updateIsZoneDialogRejectDate() {
        return userLocalDataSource.updateIsZoneDialogRejectDate(DateConverter.nowString())
    }

    override suspend fun updateSeasonOpenDialogRejected(isRejected: Boolean) {
        return userLocalDataSource.updateSeasonOpenDialogRejected(isRejected)
    }
}
package com.ilsangtech.ilsang.core.data.user.repository

import com.ilsangtech.ilsang.core.data.user.datasource.UserDataSource
import com.ilsangtech.ilsang.core.data.user.toUserInfo
import com.ilsangtech.ilsang.core.data.user.toUserXpStats
import com.ilsangtech.ilsang.core.datastore.UserDataStore
import com.ilsangtech.ilsang.core.domain.ImageRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.model.Title
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userDataStore: UserDataStore,
    private val imageRepository: ImageRepository
) : UserRepository {
    override var currentUser: MyInfo? = null

    override val shouldShowOnBoarding: Flow<Boolean> = userDataStore.shouldShowOnBoarding

    override suspend fun login(idToken: String) {
        val loginResponse = userDataSource.login(
            OAuthLoginRequest(
                idToken = idToken,
                pushToken = "",
                deviceUuid = "",
                provider = "GOOGLE",
                osType = "AOS"
            )
        )
        val (accessToken, refreshToken) = loginResponse.data
        with(userDataStore) {
            setAccessToken(accessToken)
            refreshToken?.let { setRefreshToken(refreshToken) }
        }
        updateMyInfo()
    }

    override suspend fun updateMyInfo() {
        val userInfoResponse = userDataSource.getUserInfo(userId = null)
        currentUser = MyInfo(
            completeChallengeCount = userInfoResponse.userInfoNetworkModel.completeChallengeCount,
            couponCount = userInfoResponse.userInfoNetworkModel.couponCount,
            nickname = userInfoResponse.userInfoNetworkModel.nickname,
            profileImage = userInfoResponse.userInfoNetworkModel.profileImage,
            xpPoint = userInfoResponse.userInfoNetworkModel.xpPoint,
            status = userInfoResponse.userInfoNetworkModel.status,
            title = userInfoResponse.userInfoNetworkModel.title?.let { title ->
                Title(
                    id = title.id,
                    name = title.name,
                    type = title.type,
                    condition = title.condition,
                    createdAt = title.createdAt
                )
            }
        )
    }

    override suspend fun getUserInfo(userId: String): Result<UserInfo> {
        return runCatching {
            val userInfoResponse = userDataSource.getUserInfo(userId = userId)
            val userInfoNetworkModel = userInfoResponse.userInfoNetworkModel
            userInfoNetworkModel.toUserInfo()
        }
    }

    override suspend fun logout(): Result<Unit> {
        return runCatching { userDataSource.logout() }
    }

    override suspend fun withdraw(): Result<Unit> {
        return runCatching { userDataSource.withdraw() }
    }

    override suspend fun getUserXpStats(customerId: String?): UserXpStats {
        return userDataSource.getUserXpStats(
            customerId = customerId
        ).userXpStatsNetworkModel.toUserXpStats()
    }

    override suspend fun updateUserNickname(nickname: String) {
        userDataSource.updateUserNickname(nickname = nickname)
    }

    override suspend fun updateUserImage(imageData: ByteArray): Result<Unit> {
        return runCatching {
            val imageId = imageRepository.uploadImage(
                type = "USER_PROFILE_IMAGE",
                imageBytes = imageData
            )
            userDataSource.updateUserImage(imageId)
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
}
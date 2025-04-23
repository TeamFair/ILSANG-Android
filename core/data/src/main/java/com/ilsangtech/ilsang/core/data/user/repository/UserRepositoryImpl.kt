package com.ilsangtech.ilsang.core.data.user.repository

import com.ilsangtech.ilsang.core.data.user.datasource.UserDataSource
import com.ilsangtech.ilsang.core.data.user.toUserXpStats
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override var currentUser: UserInfo? = null

    override suspend fun login(
        email: String,
        accessToken: String
    ) {
        val userResponse = userDataSource.login(
            loginRequest = LoginRequest(
                accessToken = accessToken,
                channel = "GOOGLE",
                email = email,
                refreshToken = "",
                userType = "CUSTOMER"
            )
        )
        val userInfoResponse = userDataSource.getUserInfo(
            authorization = userResponse.data.authorization
        )

        currentUser = UserInfo(
            authorization = userResponse.data.authorization,
            email = email,
            accessToken = accessToken,
            completeChallengeCount = userInfoResponse.userInfoNetworkModel.completeChallengeCount,
            couponCount = userInfoResponse.userInfoNetworkModel.couponCount,
            nickname = userInfoResponse.userInfoNetworkModel.nickname,
            profileImage = userInfoResponse.userInfoNetworkModel.profileImage,
            xpPoint = userInfoResponse.userInfoNetworkModel.xpPoint,
            status = userInfoResponse.userInfoNetworkModel.status
        )
    }

    override suspend fun updateUserInfo() {
        currentUser?.authorization?.let {
            val userInfoResponse = userDataSource.getUserInfo(
                authorization = it
            )
            currentUser = currentUser?.copy(
                completeChallengeCount = userInfoResponse.userInfoNetworkModel.completeChallengeCount,
                couponCount = userInfoResponse.userInfoNetworkModel.couponCount,
                nickname = userInfoResponse.userInfoNetworkModel.nickname,
                profileImage = userInfoResponse.userInfoNetworkModel.profileImage,
                xpPoint = userInfoResponse.userInfoNetworkModel.xpPoint,
                status = userInfoResponse.userInfoNetworkModel.status
            )
        }
    }

    override suspend fun getUserXpStats(customerId: String?): UserXpStats {
        return userDataSource.getUserXpStats(
            authorization = currentUser?.authorization!!,
            customerId = customerId
        ).userXpStatsNetworkModel.toUserXpStats()
    }

    override suspend fun updateUserNickname(nickname: String) {
        currentUser?.authorization?.let {
            runCatching {
                userDataSource.updateUserNickname(
                    authorization = it,
                    nickname = nickname
                )
            }.onSuccess {
                currentUser = currentUser?.copy(nickname = nickname)
            }
        }
    }
}
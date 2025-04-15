package com.ilsangtech.ilsang.core.data.user.repository

import com.ilsangtech.ilsang.core.data.user.datasource.UserDataSource
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.UserInfo
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
            status = userInfoResponse.userInfoNetworkModel.status
        )
    }
}
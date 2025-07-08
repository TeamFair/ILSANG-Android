package com.ilsangtech.ilsang.core.data.user.datasource

import com.ilsangtech.ilsang.core.network.api.AuthApiService
import com.ilsangtech.ilsang.core.network.api.UserApiService
import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.LoginResponse
import com.ilsangtech.ilsang.core.network.model.auth.LogoutResponse
import com.ilsangtech.ilsang.core.network.model.auth.WithdrawalResponse
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginResponse
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageDeleteResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import com.ilsangtech.ilsang.core.network.model.user.UserXpStatsResponse
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val userApiService: UserApiService
) : UserDataSource {
    override suspend fun login(loginRequest: OAuthLoginRequest): OAuthLoginResponse {
        return authApiService.oAuthLogin(loginRequest)
    }

    override suspend fun getUserInfo(userId: String?): UserInfoResponse {
        return userApiService.getUserInfo(userId)
    }

    override suspend fun logout(authorization: String): LogoutResponse {
        return authApiService.logout(authorization)
    }

    override suspend fun withdraw(authorization: String): WithdrawalResponse {
        return authApiService.withdraw(authorization)
    }

    override suspend fun getUserInfo(authorization: String, userId: String?): UserInfoResponse {
        return userApiService.getUserInfo(authorization, userId)
    }

    override suspend fun getUserXpStats(customerId: String?): UserXpStatsResponse {
        return userApiService.getUserXpStats(customerId)
    }

    override suspend fun updateUserNickname(nickname: String): NicknameUpdateResponse {
        return userApiService.updateUserNickname(NicknameUpdateRequest(nickname))
    }

    override suspend fun updateUserImage(imageId: String): UserImageUpdateResponse {
        return userApiService.updateUserImage(UserImageUpdateRequest(imageId))
    }

    override suspend fun deleteUserImage(): UserImageDeleteResponse {
        return userApiService.deleteUserImage()
    }
}

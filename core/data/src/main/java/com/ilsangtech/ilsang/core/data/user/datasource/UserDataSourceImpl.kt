package com.ilsangtech.ilsang.core.data.user.datasource

import com.ilsangtech.ilsang.core.network.api.AuthApiService
import com.ilsangtech.ilsang.core.network.api.UserApiService
import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.LoginResponse
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import com.ilsangtech.ilsang.core.network.model.user.UserXpStatsResponse
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val userApiService: UserApiService
) : UserDataSource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return authApiService.login(loginRequest)
    }

    override suspend fun getUserInfo(authorization: String): UserInfoResponse {
        return userApiService.getUserInfo(authorization)
    }

    override suspend fun getUserXpStats(
        authorization: String,
        customerId: String?
    ): UserXpStatsResponse {
        return userApiService.getUserXpStats(authorization, customerId)
    }

    override suspend fun updateUserNickname(
        authorization: String,
        nickname: String
    ): NicknameUpdateResponse {
        return userApiService.updateUserNickname(
            authorization,
            NicknameUpdateRequest(nickname)
        )
    }

    override suspend fun updateUserImage(
        authorization: String,
        imageId: String
    ): UserImageUpdateResponse {
        return userApiService.updateUserImage(
            authorization,
            UserImageUpdateRequest(imageId)
        )
    }
}

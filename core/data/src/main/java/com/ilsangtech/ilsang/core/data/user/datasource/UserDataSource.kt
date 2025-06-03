package com.ilsangtech.ilsang.core.data.user.datasource

import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.LoginResponse
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageDeleteResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import com.ilsangtech.ilsang.core.network.model.user.UserXpStatsResponse

interface UserDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun getUserInfo(authorization: String): UserInfoResponse

    suspend fun getUserXpStats(authorization: String, customerId: String?): UserXpStatsResponse

    suspend fun updateUserNickname(authorization: String, nickname: String): NicknameUpdateResponse

    suspend fun updateUserImage(authorization: String, imageId: String): UserImageUpdateResponse

    suspend fun deleteUserImage(authorization: String): UserImageDeleteResponse
}
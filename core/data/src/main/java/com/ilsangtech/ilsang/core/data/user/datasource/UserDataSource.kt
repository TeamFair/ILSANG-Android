package com.ilsangtech.ilsang.core.data.user.datasource

import com.ilsangtech.ilsang.core.network.model.auth.LogoutResponse
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginResponse
import com.ilsangtech.ilsang.core.network.model.auth.WithdrawalResponse
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageDeleteResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import com.ilsangtech.ilsang.core.network.model.user.UserXpStatsResponse

interface UserDataSource {
    suspend fun login(loginRequest: OAuthLoginRequest): OAuthLoginResponse

    suspend fun getUserInfo(userId: String?): UserInfoResponse

    suspend fun logout(): LogoutResponse

    suspend fun withdraw(): WithdrawalResponse

    suspend fun getUserXpStats(customerId: String?): UserXpStatsResponse

    suspend fun updateUserNickname(nickname: String): NicknameUpdateResponse

    suspend fun updateUserImage(imageId: String): UserImageUpdateResponse

    suspend fun deleteUserImage(): UserImageDeleteResponse
}
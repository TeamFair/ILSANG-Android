package com.ilsangtech.ilsang.core.data.user.datasource

import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageDeleteResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import com.ilsangtech.ilsang.core.network.model.user.UserTitleUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserXpStatsResponse

interface UserDataSource {
    suspend fun getUserInfo(userId: String?): UserInfoResponse

    suspend fun getUserXpStats(customerId: String?): UserXpStatsResponse

    suspend fun updateUserNickname(nickname: String): NicknameUpdateResponse

    suspend fun updateUserImage(imageId: String): UserImageUpdateResponse

    suspend fun deleteUserImage(): UserImageDeleteResponse

    suspend fun updateUserTitle(titleHistoryId: String): UserTitleUpdateResponse

    suspend fun updateUserIsZone(commericalAreaCode: String): UserInfoResponse
}
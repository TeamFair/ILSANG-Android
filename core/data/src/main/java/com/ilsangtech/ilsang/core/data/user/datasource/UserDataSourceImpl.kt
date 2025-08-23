package com.ilsangtech.ilsang.core.data.user.datasource

import com.ilsangtech.ilsang.core.network.api.UserApiService
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageDeleteResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import com.ilsangtech.ilsang.core.network.model.user.UserIsZoneUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.UserPointResponse
import com.ilsangtech.ilsang.core.network.model.user.UserTitleUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserXpStatsResponse
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserDataSource {
    override suspend fun getUserInfo(userId: String?): UserInfoResponse {
        return userApiService.getUserInfo(userId)
    }

    override suspend fun getUserPoint(userId: String?, seasonId: Int?): UserPointResponse {
        return userApiService.getUserPoint(
            userId = userId,
            seasonId = seasonId
        )
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

    override suspend fun updateUserTitle(titleHistoryId: String): UserTitleUpdateResponse {
        return userApiService.updateUserTitle(titleHistoryId)
    }

    override suspend fun updateUserIsZone(commericalAreaCode: String): UserInfoResponse {
        return userApiService.updateUserIsZone(UserIsZoneUpdateRequest(commericalAreaCode))
    }
}

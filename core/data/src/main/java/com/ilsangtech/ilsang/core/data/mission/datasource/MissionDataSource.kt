package com.ilsangtech.ilsang.core.data.mission.datasource

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.network.model.mission.ExampleMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.MissionSubmitResponse
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.UserMissionHistoryDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.UserMissionHistoryNetworkModel
import kotlinx.coroutines.flow.Flow

interface MissionDataSource {
    fun getRandomMissionHistory(): Flow<PagingData<RandomMissionHistoryNetworkModel>>

    fun exampleMissionHistory(missionId: Int): Flow<PagingData<ExampleMissionHistoryNetworkModel>>

    fun getUserMissionHistory(
        userId: String?,
        missionType: String
    ): Flow<PagingData<UserMissionHistoryNetworkModel>>

    suspend fun getUserMissionHistoryDetail(missionHistoryId: Int): UserMissionHistoryDetailNetworkModel

    suspend fun registerMissionHistoryEmoji(missionHistoryId: Int, emojiType: String)

    suspend fun deleteMissionHistoryEmoji(missionHistoryId: Int, emojiType: String)

    suspend fun reportMissionHistory(missionHistoryId: Int)

    suspend fun submitMission(
        missionId: Int,
        imageId: String? = null,
        quizId: Int? = null,
        answer: String? = null
    ): MissionSubmitResponse

    suspend fun deleteMissionHistory(missionHistoryId: Int)
}